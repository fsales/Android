package br.com.e_aluno.fragment.alunos


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.e_aluno.R
import br.com.e_aluno.firebase.firestone.UsuarioFirestone
import br.com.e_aluno.model.Usuario
import br.com.e_aluno.recyclerview.AlunoRecyclerView
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_alunos.*
import kotlinx.android.synthetic.main.fragment_noticias.view.*


class AlunosFragment : Fragment() {

    private lateinit var adapter: AlunoRecyclerView
    private lateinit var userListenerRegistration: ListenerRegistration

    private val list: ArrayList<Usuario> by lazy {
        arrayListOf<Usuario>()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        UsuarioFirestone.instance.removeListener(userListenerRegistration)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val view = inflater.inflate(R.layout.fragment_alunos, container, false)

        view.apply {
            val activityAppCompatActivity = (activity as? AppCompatActivity)

            activityAppCompatActivity?.let {
                it.setSupportActionBar(toolbar)
            }
            this.collapseLayout.title = getString(R.string.app_name)
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }

        userListenerRegistration =
                UsuarioFirestone.instance.recuperarUsuario(this.activity!!, this::updateRecyclerView, onError = {})
        return view
    }

    fun updateRecyclerView(items: ArrayList<Usuario>) {

        recyclerView.apply {
            adapter = AlunoRecyclerView(list)
            recyclerView.adapter = adapter
            adapter?.notifyDataSetChanged();

            list.addAll(items)
        }

    }


}
