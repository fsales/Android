package br.com.e_aluno.firebase.firestone

import android.content.Context
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.Aluno
import br.com.e_aluno.model.Usuario
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import java.util.*

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

    fun getCurrentUser(onComplete: (Usuario) -> Unit,
                       onError: (exception: Exception?) -> Unit?) {
        currentUserDocRef.get()
                .addOnSuccessListener {
                    it.toObject(Usuario::class.java)?.let {
                        onComplete(it)
                    }
                }.addOnFailureListener {
                    onError(it)
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
                return@addOnSuccessListener
            }

            Auth.instance.currentUser()?.let { usuario ->

                val novoUsuario = Usuario().apply {
                    this.email = usuario?.email?.toLowerCase() ?: ""
                    this.cadastro = Calendar.getInstance().time
                    this.uuid = Auth.instance.uid()
                }

                currentUserDocRef.set(novoUsuario).addOnCompleteListener {

                    val aluno = Aluno().apply {
                        this.nome = usuario?.displayName ?: novoUsuario.email?.substringBefore("@")
                        this.uuidUsuario = novoUsuario.uuid
                    }

                    AlunoFirestone.instance.criar(aluno, onComplete = { onComplete() }, onError = { onError(it) })
                }.addOnFailureListener {
                    onError(it)
                }

            }

        }.addOnFailureListener { exception ->
            onError(exception)
        }

    }


    fun updateUsuario(caminhoFoto: String,
                      onComplete: (Usuario) -> Unit,
                      onError: (Exception?) -> Unit) {
        val usuFieldMap = mutableMapOf<String, Any>()
        usuFieldMap["caminhoFoto"] = caminhoFoto

        currentUserDocRef.update(usuFieldMap).addOnCompleteListener {

            getCurrentUser(onComplete = {
                onComplete(it)
            }, onError = {
                onError(it)
            })

        }

    }

}