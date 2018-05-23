package com.androidi.fos.alunoonline.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.entity.Noticia
import kotlinx.android.synthetic.main.activity_detalhar_noticia.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.imageBitmap


class DetalharNoticia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhar_noticia)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        val noticia = intent.extras.getParcelable(INTENT_NOTICIA) as? Noticia


        noticia?.let {
            it.imagem(resources)?.let {
                featureImage.imageBitmap = it
            }

            titulo.text = it.dataNoticia + " - " + it.titulo
            desc.text =  it.descricao
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val INTENT_NOTICIA = "noticia"

        fun intent(context: Context, noticia: Noticia): Intent {
            val i = Intent(context, DetalharNoticia::class.java).apply {
                putExtra(INTENT_NOTICIA, noticia as? Parcelable)
            }

            return i
        }
    }
}
