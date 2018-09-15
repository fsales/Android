package br.com.e_aluno.fragment.noticias


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.e_aluno.R
import br.com.e_aluno.activity.noticias.DetalharNoticiaActivity
import br.com.e_aluno.recyclerview.NoticiaRecyclerView
import br.com.e_aluno.viewmodel.noticia.ListaNoticiaViewModel
import kotlinx.android.synthetic.main.fragment_noticias.view.*
import org.jetbrains.anko.support.v4.intentFor


class ListarNoticiasFragment : Fragment() {

    private var adapter: NoticiaRecyclerView? = null

    private val viewModel: ListaNoticiaViewModel by lazy {
        ViewModelProviders.of(this).get(ListaNoticiaViewModel::class.java)
    }

    companion object {
        val INTENT_NOTICIA = "noticia"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_noticias, container, false)

        view.apply {

            val activityAppCompatActivity = (activity as? AppCompatActivity)

            activityAppCompatActivity?.let {
                it.setSupportActionBar(toolbar)
            }

            recyclerView.layoutManager = LinearLayoutManager(activity)

            val lista = viewModel.noticias.value ?: arrayListOf();

            adapter = NoticiaRecyclerView(lista, clickListener = { noticia ->
                startActivity(intentFor<DetalharNoticiaActivity>().apply {
                    putExtra(INTENT_NOTICIA, noticia)
                })
            })
            recyclerView.adapter = adapter
            adapter?.notifyDataSetChanged();
            this.collapseLayout.title = getString(R.string.app_name)


        }

        viewModel.noticias.observe(this, Observer { list ->
            list?.let { listaNoticia ->
                adapter?.let { adp ->
                    adp.list = listaNoticia
                    adp.notifyDataSetChanged();
                }
            }
        })


        return view
    }


}
