package br.com.e_aluno.firebase

import br.com.e_aluno.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class Auth {
    companion object {
        val instance: Auth by lazy {
            Auth()
        }
    }


    private val instanceAuthFirebase: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun createUserWithEmailAndPassword(usuario: Usuario, onComplete: () -> Unit, onError: (exception: Exception) -> Unit) {

        instanceAuthFirebase?.let { mAuth ->

            val criarUsuario = mAuth.createUserWithEmailAndPassword(usuario.email!!.toLowerCase(), usuario.senha!!)
            criarUsuario.addOnCompleteListener { task ->

                try {
                    if (task.isSuccessful) {
                        onComplete()
                    }

                    task.exception?.let {
                        onError(it)
                    }

                } catch (firebaseAuthUserException: FirebaseAuthUserCollisionException) {
                    onError(firebaseAuthUserException)
                }
            }
        }
    }

    fun uid() = instanceAuthFirebase.uid

    fun currentUser() = instanceAuthFirebase.currentUser
}