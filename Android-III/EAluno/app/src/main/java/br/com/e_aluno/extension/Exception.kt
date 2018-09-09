package br.com.e_aluno.extension

import br.com.e_aluno.EAlunoActivity
import br.com.e_aluno.enum.FirebaseAuthError
import com.google.firebase.auth.FirebaseAuthException

fun capturarMensagemErro(exception: Exception?, onComplete: (mesagemErro: String) -> Unit) {

    exception?.let { ex ->
        var msgError = FirebaseAuthError.ERROR_UNKNOWN.description

        when (ex) {
            is FirebaseAuthException -> {
                val firebaseAuthError = FirebaseAuthError.fromException(ex)
                msgError = firebaseAuthError.description
            }
        }

        onComplete(msgError)
    }
}