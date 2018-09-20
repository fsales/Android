package br.com.e_aluno.fragment.noticias


import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.e_aluno.AppContantes.INTENT_NOTICIA
import br.com.e_aluno.R
import br.com.e_aluno.activity.noticias.DetalharNoticiaActivity
import br.com.e_aluno.extension.dialogCarregando
import br.com.e_aluno.firebase.firestone.NoticiasFirestone
import br.com.e_aluno.model.Noticia
import br.com.e_aluno.recyclerview.NoticiaRecyclerView
import br.com.e_aluno.viewmodel.galeria.GaleriaViewModel
import br.com.e_aluno.viewmodel.noticia.ListaNoticiaViewModel
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_noticias.view.*
import org.jetbrains.anko.support.v4.intentFor


class ListarNoticiasFragment : Fragment() {

    private lateinit var dialogProgress: ProgressDialog
    private var adapter: NoticiaRecyclerView? = null
    private lateinit var noticiaListenerRegistration: ListenerRegistration

    private val viewModel: ListaNoticiaViewModel by lazy {
        ViewModelProviders.of(this).get(ListaNoticiaViewModel::class.java)
    }

    private val viewModelGaleria: GaleriaViewModel by lazy {
        ViewModelProviders.of(this).get(GaleriaViewModel::class.java)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dialogProgress = dialogCarregando()
    }

    override fun onDetach() {
        super.onDetach()
        dialogProgress.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        NoticiasFirestone.instance.removeListener(noticiaListenerRegistration)
        dialogProgress.dismiss()
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
            this.collapseLayout.title = getString(R.string.app_name)

            dialogProgress.dismiss()
        }

        viewModel.noticias.observe(this, Observer { list ->

            list?.let { listaNoticia ->
                adapter?.let { adp ->
                    dialogProgress.show()
                    adp.list = listaNoticia
                    adp.notifyDataSetChanged();
                }
            }
        })

        noticiaListenerRegistration =
                NoticiasFirestone.instance.noticias(this::updateRecyclerView)
        return view
    }

    fun updateRecyclerView(itens: ArrayList<Noticia>) {
        viewModel.noticias.value = itens
        dialogProgress.dismiss()
    }


}
