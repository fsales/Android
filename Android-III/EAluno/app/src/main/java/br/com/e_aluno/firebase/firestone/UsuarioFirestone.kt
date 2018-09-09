package br.com.e_aluno.firebase.firestone

import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.Usuario
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class UsuarioFirestone {

    companion object {
        val instance: UsuarioFirestone by lazy {
            UsuarioFirestone()
        }
    }

    private val instance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val currentUserDocRef: DocumentReference
        get() = instance.document("usuarios/${Auth.instance.uid()}")
                ?: throw  NullPointerException("UID está nulo")


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