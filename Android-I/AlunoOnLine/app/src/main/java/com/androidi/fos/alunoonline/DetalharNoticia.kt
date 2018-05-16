package com.androidi.fos.alunoonline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import com.androidi.fos.alunoonline.entity.Noticia
import kotlinx.android.synthetic.main.activity_detalhar_noticia.*
import kotlinx.android.synthetic.main.card_noticia.view.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.toast


class DetalharNoticia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhar_noticia)
        setSupportActionBar(toolbar)


        val noticia = intent.extras.getParcelable(DetalharNoticia.INTENT_NOTICIA) as? Noticia


        noticia?.let {
            it.imagem(resources)?.let {
                featureImage.imageBitmap = it
            }

            titulo.text = it.dataNoticia + " - " + it.titulo
            desc.text =  it.descricao
        }


    }

    companion object {
        val INTENT_NOTICIA = "noticia"

        fun intent(context: Context, noticia: Noticia): Intent {
            val i = Intent(context, DetalharNoticia::class.java).apply {
                putExtra(DetalharNoticia.INTENT_NOTICIA, noticia as? Parcelable)
            }

            return i
        }
    }
}
