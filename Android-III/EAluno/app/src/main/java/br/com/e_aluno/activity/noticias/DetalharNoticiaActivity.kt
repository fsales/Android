package br.com.e_aluno.activity.noticias

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import br.com.e_aluno.AppContantes.INTENT_NOTICIA
import br.com.e_aluno.EAlunoActivity
import br.com.e_aluno.R
import br.com.e_aluno.firebase.Storage
import br.com.e_aluno.formataDataHora
import br.com.e_aluno.model.Noticia
import br.com.e_aluno.viewmodel.noticia.DetalharNoticiaViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detalhar_noticia.*
import kotlinx.android.synthetic.main.toolbar.*

class DetalharNoticiaActivity : EAlunoActivity() {

    private val viewModel: DetalharNoticiaViewModel by lazy {
        ViewModelProviders.of(this).get(DetalharNoticiaViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhar_noticia)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }



        viewModel.noticia.value = intent.extras.getParcelable(INTENT_NOTICIA) as? Noticia

        viewModel.noticia.observe(this, Observer { noticia ->
            noticia?.let { it ->
                /*it.imagem(resources)?.let {
                    featureImage.imageBitmap = it
                }*/

                it.imagemPath?.let { imagemPath ->
                    if (imagemPath.isNotEmpty()) {
                        val options = RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.ic_image_black_24dp)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform()

                        Glide.with(applicationContext)
                                .load(Storage.INSTANCE.pathToReference(imagemPath))
                                .apply(options)
                                .into(this.featureImage)
                                .clearOnDetach()
                    }
                }

                var data = ""
                it.dataNoticia?.let {
                    data = formataDataHora(it)
                }
                titulo.text = data + " - " + it.titulo
                desc.text = it.descricao
            }
        })
    }
}
