package com.androidi.fos.recyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {

    lateinit var dataSource: List<Usuario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        dataSource = listOf<Usuario>(
                Usuario(nome = "Camilo"),
                Usuario(nome = "Matheus"),
                Usuario(nome = "Arthur"))

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdaptadorUsuario(dataSource)
        recyclerView.adapter.notifyDataSetChanged()

    }

    inner class AdaptadorUsuario(private val dataSource: List<Usuario>) : RecyclerView.Adapter<UsuarioViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.usuario_item_lista, parent, false)

            return UsuarioViewHolder(v)
        }

        override fun getItemCount(): Int {
            return dataSource.size
        }

        override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
            var dado = dataSource[position]
            holder.nomeUsuario.text = dado.nome
        }


    }

    inner class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nomeUsuario: TextView

        init {
            nomeUsuario = itemView.findViewById(R.id.nomeUsuarioTextView)
        }
    }
}


class Usuario(var nome: String? = "")
