package com.androidi.fos.alunoonline.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.entity.Noticia
import com.androidi.fos.alunoonline.recyclerview.RecyclerViewNoticia
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    private var adapter: RecyclerViewNoticia? = null
    private val list = arrayListOf<Noticia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar);
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewNoticia(list)
        recyclerView.adapter = adapter



        list.addAll(arrayListOf<Noticia>(Noticia(id = 1, descricao = "Teste 1", titulo = "Teste 11111"),
                Noticia(id = 2, descricao = "Teste 2", titulo = "Teste 11111"),
                Noticia(id = 3, descricao = "Teste 3", titulo = "Teste 11111")))
        adapter?.notifyDataSetChanged();
    }
}
