package br.com.e_aluno.viewmodel.noticia

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.e_aluno.firebase.firestone.NoticiasFirestone
import br.com.e_aluno.model.Noticia

class ListaNoticiaViewModel : ViewModel() {

    val noticias: MutableLiveData<List<Noticia>> by lazy {
        MutableLiveData<List<Noticia>>().apply {
            value = listOf()
        }
    }

    init {
        NoticiasFirestone.instance.noticias {
            noticias.value = it
        }
    }
}