package br.com.e_aluno.activity.noticias

import android.os.Bundle
import br.com.e_aluno.EAlunoActivity
import br.com.e_aluno.R
import br.com.e_aluno.fragment.noticias.ListarNoticiasFragment.Companion.INTENT_NOTICIA
import br.com.e_aluno.model.Noticia
import kotlinx.android.synthetic.main.activity_detalhar_noticia.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.imageBitmap

class DetalharNoticiaActivity : EAlunoActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhar_noticia)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val noticia = intent.extras.getParcelable(INTENT_NOTICIA) as? Noticia

        noticia?.let {
            it.imagem(resources)?.let {
                featureImage.imageBitmap = it
            }

            titulo.text = it.dataNoticia + " - " + it.titulo
            desc.text =  it.descricao
        }
    }
}
