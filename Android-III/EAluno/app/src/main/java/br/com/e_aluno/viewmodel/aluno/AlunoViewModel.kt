package br.com.e_aluno.viewmodel.aluno

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.e_aluno.model.Usuario

class AlunoViewModel() : ViewModel() {


    val usuarios: MutableLiveData<ArrayList<Usuario>> by lazy {
        MutableLiveData<ArrayList<Usuario>>().apply {
            value = arrayListOf()
        }
    }
}