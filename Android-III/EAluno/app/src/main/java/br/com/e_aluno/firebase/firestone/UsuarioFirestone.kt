package br.com.e_aluno.firebase.firestone

import android.content.Context
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.Usuario
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class UsuarioFirestone  {

    companion object {
        val instance: UsuarioFirestone by lazy {
            UsuarioFirestone()
        }
    }

    private val PATH_USUARIOS: String by lazy {
        "usuarios"
    }

    private val instance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val currentUserDocRef: DocumentReference
        get() = instance.document("${PATH_USUARIOS}/${Auth.instance.uid()}")
                ?: throw  NullPointerException("UID está nulo")

    fun getCurrentUser(onComplete: (Usuario) -> Unit) {
        currentUserDocRef.get()
                .addOnSuccessListener {
                    onComplete(it.toObject(Usuario::class.java)!!)
                }
    }

    fun recuperarUsuario(context: Context,
                         onLista: (ArrayList<Usuario>) -> Unit
    ): ListenerRegistration {
        return instance.collection("${PATH_USUARIOS}")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                    firebaseFirestoreException?.let {
                        error(it)
                        return@addSnapshotListener
                    }

                    val itens = arrayListOf<Usuario>()
                    querySnapshot?.let {
                        it.documents.forEach {
                            if (it.id != Auth.instance.uid()) {
                                val usuario: Usuario? = it.toObject(Usuario::class.java)
                                usuario?.let {
                                    itens.add(it)
                                }
                            }
                        }

                        onLista(itens)

                    }
                }
    }

    fun removeListener(registration: ListenerRegistration) = registration.remove()

    fun criarUsuario(onComplete: () -> Unit, onError: (exception: Exception?) -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot.exists()) {
                onComplete()
            } else {

                val novoUsuario = Usuario().apply {
                    Auth.instance.currentUser()?.let { currentUser ->
                        this.email = currentUser.email?.toLowerCase() ?: ""
                        this.nome = currentUser.displayName ?: ""
                        this.cadastro = Timestamp.now()
                    }
                }

                currentUserDocRef.set(novoUsuario).addOnCompleteListener { onComplete() }
            }
        }.addOnFailureListener { exception ->
            onError(exception)
        }

    }

}