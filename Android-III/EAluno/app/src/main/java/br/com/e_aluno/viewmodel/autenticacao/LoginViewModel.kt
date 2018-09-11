package br.com.e_aluno.viewmodel.autenticacao

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.e_aluno.extension.capturarMensagemErro
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.Usuario

class LoginViewModel() : ViewModel() {
    
    val usuario: MutableLiveData<Usuario> by lazy {
        MutableLiveData<Usuario>().apply {
            value = Usuario()
        }
    }

    fun updateValueUsuario(usuario: Usuario) {
        this.usuario.value = usuario
    }

    fun signInWithEmailAndPassword(onComplete: () -> Unit,
                                   onError: (String?) -> Unit) {
        usuario.value?.let { usuario ->
            Auth.instance.signInWithEmailAndPassword(usuario, onComplete = {
                onComplete()
            }, onError = { exception ->
                capturarMensagemErro(exception) {
                    onError(it)
                }
            })
        }

    }
}