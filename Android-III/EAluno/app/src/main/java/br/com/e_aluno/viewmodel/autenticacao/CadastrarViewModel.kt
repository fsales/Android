package br.com.e_aluno.viewmodel.autenticacao

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.firebase.firestone.UsuarioFirestone
import br.com.e_aluno.model.Usuario

class CadastrarViewModel : ViewModel {

    constructor() : super()

    val usuario = MutableLiveData<Usuario>()

    fun createUserWithEmailAndPassword(onComplete: () -> Unit, onError: (mensagem: String?) -> Unit?) {

        usuario.value?.let { usuario ->
            Auth.instance.createUserWithEmailAndPassword(usuario, onComplete = {
                UsuarioFirestone.instance.criarUsuario(onComplete = {
                    this.usuario.value = Usuario()
                    onComplete()
                }, onError = { exception ->
                    exception?.let { e -> error(e) }
                    onError
                })

            }, onError = { exception ->
                error(exception)
                onError
            })
        }

    }
}