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

    fun sendPasswordResetEmail(usuario: Usuario,
                               onComplete: () -> Unit,
                               onError: (exception: Exception?) -> Unit) {
        try {
            val resetPassword = instanceAuthFirebase.sendPasswordResetEmail(usuario.email!!)
            resetPassword.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete()
                    return@addOnCompleteListener
                }

                onError(task.exception)
            }
        } catch (firebaseAuthUserException: FirebaseAuthUserCollisionException) {
            onError(firebaseAuthUserException)
        }
    }

    fun signInWithEmailAndPassword(usuario: Usuario,
                                   onComplete: () -> Unit,
                                   onError: (exception: Exception?) -> Unit) {

        try {
            val signIn = instanceAuthFirebase.signInWithEmailAndPassword(usuario.email!!.toLowerCase(), usuario.senha!!)
            signIn.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentUser()?.reload()
                    onComplete()
                    return@addOnCompleteListener
                }

                onError(task.exception)

            }.addOnFailureListener { exception ->
                onError(exception)
            }
        } catch (firebaseAuthUserException: FirebaseAuthUserCollisionException) {
            onError(firebaseAuthUserException)
        }

    }

    fun createUserWithEmailAndPassword(usuario: Usuario,
                                       onComplete: () -> Unit,
                                       onError: (exception: Exception?) -> Unit) {

        val criarUsuario = instanceAuthFirebase.createUserWithEmailAndPassword(usuario.email!!.toLowerCase(), usuario.senha!!)
        criarUsuario.addOnCompleteListener { task ->

            try {
                if (task.isSuccessful) {
                    onComplete()
                    return@addOnCompleteListener
                }

                onError(task.exception)

            } catch (firebaseAuthUserException: FirebaseAuthUserCollisionException) {
                onError(firebaseAuthUserException)
            }
        }

    }

    fun uid() = instanceAuthFirebase.uid

    fun currentUser() = instanceAuthFirebase.currentUser
}