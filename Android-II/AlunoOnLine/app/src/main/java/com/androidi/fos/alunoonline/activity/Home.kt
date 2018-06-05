package com.androidi.fos.alunoonline.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.entity.Noticia
import com.androidi.fos.alunoonline.recyclerview.RecyclerViewNoticia
import com.androidi.fos.alunoonline.extension.mockNoticias
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    private var adapter: RecyclerViewNoticia? = null
    private val list = arrayListOf<Noticia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar);
        collapseLayout.title = getString(R.string.app_name)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewNoticia(list)
        recyclerView.adapter = adapter

        list.addAll(mockNoticias())

        val a = R.drawable.senhores
        adapter?.notifyDataSetChanged();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menuUsuario -> {
            startActivity(Intent(this@Home, AlterarUsuario::class.java))
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }
}
