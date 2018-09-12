package br.com.e_aluno.fragment.noticias


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.e_aluno.R
import br.com.e_aluno.extension.mockNoticias
import br.com.e_aluno.model.Noticia
import br.com.e_aluno.recyclerview.NoticiaRecyclerView
import kotlinx.android.synthetic.main.fragment_noticias.view.*


class ListarNoticiasFragment : Fragment() {

    private var adapter: NoticiaRecyclerView? = null
    private val list: ArrayList<Noticia> by lazy {
        arrayListOf<Noticia>()
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
            adapter = NoticiaRecyclerView(list)
            recyclerView.adapter = adapter



            list.addAll(mockNoticias())
            adapter?.notifyDataSetChanged();
            this.collapseLayout.title = getString(R.string.app_name)

        }
        return view
    }


}
