package br.com.e_aluno.activity.noticias

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import br.com.e_aluno.EAlunoActivity
import br.com.e_aluno.R
import br.com.e_aluno.fragment.noticias.ListarNoticiasFragment.Companion.INTENT_NOTICIA
import br.com.e_aluno.model.Noticia
import br.com.e_aluno.viewmodel.noticia.DetalharNoticiaViewModel
import kotlinx.android.synthetic.main.activity_detalhar_noticia.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.imageBitmap

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
                it.imagem(resources)?.let {
                    featureImage.imageBitmap = it
                }

                titulo.text = it.dataNoticia + " - " + it.titulo
                desc.text = it.descricao
            }
        })
    }
}
