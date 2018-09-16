package br.com.e_aluno.viewmodel.aluno

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import br.com.e_aluno.extension.capturarMensagemErro
import br.com.e_aluno.firebase.Storage
import br.com.e_aluno.firebase.firestone.AlunoFirestone
import br.com.e_aluno.firebase.firestone.UsuarioFirestone
import br.com.e_aluno.model.Aluno
import br.com.e_aluno.model.Usuario

class AlunoViewModel : ViewModel() {

    val usuario: MutableLiveData<Usuario> by lazy {
        MutableLiveData<Usuario>().apply {
            value = Usuario()
        }
    }

    val aluno: MutableLiveData<Aluno> by lazy {
        MutableLiveData<Aluno>().apply {
            value = Aluno()
        }
    }

    init {
        carregarUsuarioLogado()
    }


    private fun carregarUsuarioLogado() {
        UsuarioFirestone.instance.getCurrentUser(onComplete = { usuario ->
            this.usuario.postValue(usuario)
        }, onError = { exception ->
            capturarMensagemErro(exception) {
                Log.e("erro", exception.toString())
            }
        })
    }


    fun updateValueAluno(aluno: Aluno) {
        this.aluno.value = aluno
    }

    fun carregarDadosAluno(onComplete: () -> Unit,
                           onErro: (exception: String?) -> Unit) {
        AlunoFirestone.instance.getAluno(onComplete = {
            this.aluno.value = it
            onComplete()
        }, onError = { exception ->
            capturarMensagemErro(exception) {
                onErro(it)
            }
        })
    }

    fun criarAluno(imagemUsuario: ByteArray? = null,
                   onComplete: () -> Unit,
                   onError: (String?) -> Unit) {

        imagemUsuario?.let {
            Storage.INSTANCE.uploadFoto(it, onComplete = {
                UsuarioFirestone.instance.updateUsuario(it, onComplete = {
                    usuario.postValue(it)
                    onComplete()
                }, onError = {
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

        this.aluno.value?.let { aluno ->

            usuario.value?.let {
                aluno.uuidUsuario = it.uuid

                AlunoFirestone.instance.criar(aluno, onComplete = {
                    onComplete()
                }, onError = { exception ->
                    capturarMensagemErro(exception) { msg ->
                        onError(msg)
                    }
                })
            }

        }

    }

}