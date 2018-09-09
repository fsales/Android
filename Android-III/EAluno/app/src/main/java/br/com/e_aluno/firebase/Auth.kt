package br.com.e_aluno.firebase

import br.com.e_aluno.extension.capturarMensagemErro
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

    fun signInWithEmailAndPassword(usuario: Usuario,
                                   onComplete: () -> Unit,
                                   onError: (msgErro: String?) -> Unit) {

        val signIn = instanceAuthFirebase.signInWithEmailAndPassword(usuario.email!!.toLowerCase(), usuario.senha!!)
        signIn.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onComplete()
                return@addOnCompleteListener
            }
            onError

        }.addOnFailureListener { exception ->
            capturarMensagemErro(exception) {
                onError(it)
            }
        }

    }

    fun createUserWithEmailAndPassword(usuario: Usuario,
                                       onComplete: () -> Unit,
                                       onError: (exception: Exception) -> Unit) {

        val criarUsuario = instanceAuthFirebase.createUserWithEmailAndPassword(usuario.email!!.toLowerCase(), usuario.senha!!)
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

    fun uid() = instanceAuthFirebase.uid

    fun currentUser() = instanceAuthFirebase.currentUser
}