package br.com.e_aluno.viewmodel.autenticacao

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.e_aluno.extension.capturarMensagemErro
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.firebase.firestone.UsuarioFirestone
import br.com.e_aluno.model.Usuario
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class CadastrarViewModel : ViewModel {

    constructor() : super()

    val usuario = MutableLiveData<Usuario>()

    fun createUserWithEmailAndPassword(onComplete: () -> Unit, onError: (mensagem: String?) -> Unit?) {

        try {
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
                    capturarMensagemErro(exception){
                        onError(it)
                    }

                })
            }
        } catch (firebaseAuthUserException: FirebaseAuthUserCollisionException) {
            capturarMensagemErro(firebaseAuthUserException){
                onError(it)
            }
        }
    }
}