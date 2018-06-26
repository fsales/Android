package com.androidi.fos.recyclerview

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.SearchView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {

    var dataSource: MutableList<Usuario> = mutableListOf<Usuario>()
    lateinit var adptador: AdaptadorUsuario

    val dadosRecyclerView = mutableListOf<Usuario>(
            Usuario(nome = "Camilo"),
            Usuario(nome = "Matheus"),
            Usuario(nome = "Arthur"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adptador = AdaptadorUsuario(dataSource, this)
        recyclerView.adapter = adptador
        //  recyclerView.adapter.notifyDataSetChanged()

        editsearch.queryHint = "Teste"

        editsearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adptador.filter.filter(query)
                return false
            }

        })

        dataSource.addAll(dadosRecyclerView)

    }


    inner class AdaptadorUsuario(private var lista: List<Usuario>, private var context: Context) : RecyclerView.Adapter<UsuarioViewHolder>(), Filterable {
        internal var filterList: List<Usuario>

        init {
            filterList = lista
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
            val v = LayoutInflater.from(context).inflate(R.layout.usuario_item_lista, parent, false)

            return UsuarioViewHolder(v)
        }

        override fun getItemCount(): Int {
            return lista.size
        }

        override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
            var dado = lista[position]
            holder.nomeUsuario.text = dado.nome
        }

        // filtro de usuário
        override fun getFilter(): Filter {
            return CustomFilter(this, filterList)
        }

        inner class CustomFilter(private val adaptador: AdaptadorUsuario, private val lista: List<Usuario>) : Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                var result = Filter.FilterResults()
                val newLista = mutableListOf<Usuario>()

                if (query?.isNotEmpty()!!) {
                    lista.forEach { usu ->
                        if (usu.nome?.contains(query!!, true)!!) {
                            newLista.add(usu)
                        }
                    }

                }

                if (newLista.size > 0) {
                    result.count = newLista.size
                    result.values = newLista
                } else {
                    result.count = lista.size
                    result.values = lista
                }

                return result
            }

            override fun publishResults(char: CharSequence?, results: FilterResults?) {
                results?.let { r ->
                    var lista = r.values  as? List<Usuario>
                    lista?.let { l ->
                        adaptador.lista = l
                    }
                }
                adaptador.notifyDataSetChanged()
            }

            private fun pesquisar(array: List<String>, chaveBusca: String) {
                val a = array.sorted()
                val indice = pesquisaBinaria(a, 0, a.size - 1, chaveBusca)
                if (indice >= 0) println(">>>>>>> ${a[indice]}") else print("não encontrado")
            }


            private fun pesquisaBinaria(array: List<String>, elementoMenor: Int, elementoMaior: Int, chaveBusca: String): Int {
                val media = (elementoMaior + elementoMenor) / 2
                val valorMeio = array.get(media)

                return if (elementoMenor > elementoMaior) {
                    -1
                } else if (valorMeio.equals(chaveBusca)) {
                    media
                } else if (valorMeio.compareTo(chaveBusca) <0) {
                    pesquisaBinaria(array, media + 1, elementoMaior, chaveBusca)
                } else {
                    pesquisaBinaria(array, elementoMenor, media - 1, chaveBusca)
                }
            }

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
