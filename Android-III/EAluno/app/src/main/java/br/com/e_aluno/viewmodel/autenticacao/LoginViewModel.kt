package br.com.e_aluno.viewmodel.autenticacao

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.widget.Toast
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.Usuario

class LoginViewModel : ViewModel() {

    val usuario = MutableLiveData<Usuario>()

    fun signInWithEmailAndPassword(onComplete: () -> Unit,
                                   onError: (String?) -> Toast) {
        usuario.value?.let { usuario ->
            Auth.instance.signInWithEmailAndPassword(usuario, onComplete = {
                onComplete()
            }, onError = { msgErro ->
                onError(msgErro)
            })
        }

    }
}