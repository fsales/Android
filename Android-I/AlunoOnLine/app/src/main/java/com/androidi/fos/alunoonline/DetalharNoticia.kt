package com.androidi.fos.alunoonline

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.androidi.fos.alunoonline.entity.Noticia
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast


class DetalharNoticia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhar_noticia)
        setSupportActionBar(toolbar)
      //  val a = intent.extras.getSerializable("PUT_EXTRA_NOTICIA") as Noticia

        toast("tse")
    }
}
