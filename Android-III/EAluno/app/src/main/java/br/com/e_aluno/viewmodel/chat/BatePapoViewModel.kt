package br.com.e_aluno.viewmodel.chat

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.e_aluno.extension.capturarMensagemErro
import br.com.e_aluno.firebase.Storage
import br.com.e_aluno.firebase.firestone.ChatFirestore
import br.com.e_aluno.firebase.firestone.UsuarioFirestone
import br.com.e_aluno.model.*
import java.io.ByteArrayOutputStream

class BatePapoViewModel : ViewModel() {

    val usuarioCorrente: MutableLiveData<Usuario> = MutableLiveData<Usuario>()

    val mensagem: MutableLiveData<IMensagem> by lazy {
        MutableLiveData<IMensagem>().apply {
            value = MensagemTexto()
        }
    }

    val mensagemImagem: MutableLiveData<MensagemImagem> by lazy {
        MutableLiveData<MensagemImagem>().apply {
            value = MensagemImagem()
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


    fun enviarMensagemTexto(idCanal: String,
                            onComplete: () -> Unit,
                            onError: (String?) -> Unit?) {
        ChatFirestore.instance.sendMessage(mensagem.value!!, idCanal, onComplete = {
            onComplete()
        }, onErro = {
            capturarMensagemErro(it) { msg ->
                onError(msg)
            }
        })
    }

    fun enviarMensagemImagem(idCanal: String,
                             imagemBytes: ByteArrayOutputStream,
                             onComplete: () -> Unit,
                             onError: (String?) -> Unit?) {

        Storage.INSTANCE.uploadFoto(imagemBytes = imagemBytes.toByteArray(), onComplete = {
            mensagemImagem.value.apply {
                this?.imagemPath = it
            }

            ChatFirestore.instance.sendMessage(mensagemImagem.value!!, idCanal, onComplete = {
                onComplete()
            }, onErro = {
                capturarMensagemErro(it) { msg ->
                    onError(msg)
                }
            })


        }, onError = {
            capturarMensagemErro(it) { msg ->
                onError(msg)
            }
        })
    }

}