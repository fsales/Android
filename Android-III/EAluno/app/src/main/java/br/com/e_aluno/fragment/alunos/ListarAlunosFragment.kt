package br.com.e_aluno.fragment.alunos


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
import br.com.e_aluno.AppContantes
import br.com.e_aluno.R
import br.com.e_aluno.activity.batepapo.BatePapoActivity
import br.com.e_aluno.extension.dialogCarregando
import br.com.e_aluno.firebase.firestone.UsuarioFirestone
import br.com.e_aluno.model.Usuario
import br.com.e_aluno.recyclerview.AlunoRecyclerView
import br.com.e_aluno.viewmodel.aluno.ListarAlunosViewModel
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_noticias.view.*
import org.jetbrains.anko.support.v4.intentFor


class ListarAlunosFragment : Fragment() {

    private lateinit var adapter: AlunoRecyclerView
    private lateinit var userListenerRegistration: ListenerRegistration
    private lateinit var dialogProgress: ProgressDialog

    private val viewModel: ListarAlunosViewModel by lazy {
        ViewModelProviders.of(this).get(ListarAlunosViewModel::class.java)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        UsuarioFirestone.instance.removeListener(userListenerRegistration)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dialogProgress = dialogCarregando()
    }

    override fun onDetach() {
        super.onDetach()
        dialogProgress.dismiss()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_listar_alunos, container, false)

        view.run {


            val activityAppCompatActivity = (activity as? AppCompatActivity)

            activityAppCompatActivity?.let {
                it.setSupportActionBar(toolbar)
            }

            recyclerView.layoutManager = LinearLayoutManager(activity)
            adapter = AlunoRecyclerView(context = context!!, clickListerner = { usuario ->

                startActivity(intentFor<BatePapoActivity>().apply {
                    putExtra(AppContantes.INTENT_USUARIO, usuario)
                })
            })

            this.recyclerView.adapter = adapter
            dialogProgress.dismiss()
        }

        viewModel.usuarios.observe(this, Observer { list ->

            list?.let {
                dialogProgress.show()
                adapter.list = it
                adapter?.notifyDataSetChanged();

            }
        })

        userListenerRegistration =
                UsuarioFirestone.instance.recuperarUsuario(this::updateRecyclerView)
        return view
    }

    fun updateRecyclerView(itens: ArrayList<Usuario>) {
        viewModel.usuarios.value = itens
        dialogProgress.dismiss()
    }


}
