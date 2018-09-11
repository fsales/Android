package br.com.e_aluno.viewmodel.autenticacao

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.e_aluno.extension.capturarMensagemErro
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.Usuario

class RecuperarSenhaViewModel() : ViewModel() {

    val usuario: MutableLiveData<Usuario> by lazy {
        MutableLiveData<Usuario>().apply {
            value = Usuario()
        }
    }

    fun updateValueUsuario(usuario: Usuario) {
        this.usuario.value = usuario
    }

    fun sendPasswordResetEmail(onComplete: (String) -> Unit,
                               onError: (String?) -> Unit) {
        usuario.value?.let { usu ->
            Auth.instance.sendPasswordResetEmail(usu, onComplete = {
                onComplete(String.format("Enviado para o endereço %s, um e-mail com as informações de recuperação.", usu.email))
            }, onError = { exception ->
                capturarMensagemErro(exception) {
                    onError(it)
                }
            })
        }
        //
    }

}
