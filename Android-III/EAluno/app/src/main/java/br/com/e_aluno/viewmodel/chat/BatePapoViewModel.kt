package br.com.e_aluno.viewmodel.chat

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.e_aluno.extension.capturarMensagemErro
import br.com.e_aluno.firebase.firestone.ChatFirestore
import br.com.e_aluno.firebase.firestone.UsuarioFirestone
import br.com.e_aluno.model.IMensagem
import br.com.e_aluno.model.Mensagem
import br.com.e_aluno.model.MensagemTexto
import br.com.e_aluno.model.Usuario

class BatePapoViewModel : ViewModel() {

    val usuarioCorrente: MutableLiveData<Usuario> = MutableLiveData<Usuario>()

    val mensagem: MutableLiveData<IMensagem> by lazy {
        MutableLiveData<IMensagem>().apply {
            value = MensagemTexto()
        }
    }

    val otherUsuario: MutableLiveData<Usuario> by lazy {
        MutableLiveData<Usuario>().apply {
            value = Usuario()
        }
    }

    val mesagens: MutableLiveData<ArrayList<Mensagem>> by lazy {
        MutableLiveData<ArrayList<Mensagem>>().apply {
            value = arrayListOf()
        }
    }


    init {
        UsuarioFirestone.instance.getCurrentUser(onComplete = {
            usuarioCorrente.postValue(it)
        }, onError = {
            throw  NullPointerException("UID está nulo")
        })
    }


    fun enviarMensagem(idCanal: String,
                       onComplete: () -> Unit ,
                       onError: (String?) -> Unit?) {
        ChatFirestore.instance.sendMessage(mensagem.value!!, idCanal, onComplete = {
            onComplete()
        }, onErro = {
            capturarMensagemErro(it) { msg ->
                onError(msg)
            }
        })
    }

}