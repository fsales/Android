package br.com.e_aluno.viewmodel.noticia

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.e_aluno.model.Noticia

class DetalharNoticiaViewModel : ViewModel() {

    val noticia: MutableLiveData<Noticia> by lazy {
        MutableLiveData<Noticia>().apply {
            value = Noticia()
        }
    }
}