package br.com.e_aluno.viewmodel.aluno

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.e_aluno.extension.capturarMensagemErro
import br.com.e_aluno.firebase.firestone.AlunoFirestone
import br.com.e_aluno.model.Aluno

class AlunoViewModel : ViewModel() {

    val aluno: MutableLiveData<Aluno> by lazy {
        MutableLiveData<Aluno>().apply {
            value = Aluno()
        }
    }

    fun updateValueAluno(aluno: Aluno) {
        this.aluno.value = aluno
    }

    fun criarAluno(onComplete: () -> Unit,
                   onError: (String?) -> Unit) {

        this.aluno.value?.let { aluno ->
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