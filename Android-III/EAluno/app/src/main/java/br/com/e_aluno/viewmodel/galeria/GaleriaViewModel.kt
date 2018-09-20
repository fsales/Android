package br.com.e_aluno.viewmodel.galeria

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.e_aluno.firebase.firestone.BannerFirestone
import br.com.e_aluno.model.Banner

class GaleriaViewModel : ViewModel() {

    init {
        carregarBanner()
    }

    val banner: MutableLiveData<List<Banner>> by lazy {
        MutableLiveData<List<Banner>>().apply {
            value = listOf<Banner>()
        }
    }


    fun carregarBanner() {
        val a = BannerFirestone.instance.getBanners(onListen = {
            banner.value = it
        })
    }
}