package br.com.e_aluno.fragment.alunos


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.e_aluno.R
import br.com.e_aluno.extension.campoPreenchido
import br.com.e_aluno.extension.dialogCarregando
import br.com.e_aluno.extension.mensagemCampoObrigatorio
import br.com.e_aluno.fragment.MenuFragment
import br.com.e_aluno.model.Aluno
import br.com.e_aluno.viewmodel.aluno.AlunoViewModel
import kotlinx.android.synthetic.main.fragment_aluno.*
import kotlinx.android.synthetic.main.fragment_aluno.view.*
import kotlinx.android.synthetic.main.fragment_noticias.view.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast


class AlunoFragment : MenuFragment() {
/*
    private val viewModel: AlunoViewModel by lazy {
        ViewModelProviders.of(this).get(AlunoViewModel::class.java)
    }*/


    private  lateinit var viewModel: AlunoViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this).get(AlunoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_aluno, container, false)
        setHasOptionsMenu(true);
        view.run {

            val activityAppCompatActivity = (activity as? AppCompatActivity)
            activityAppCompatActivity?.let {
                it.setSupportActionBar(toolbar)

            }

            this.confirmarBotton.setOnClickListener {
                salvar()
            }
        }

        viewModel.aluno.observe(this, Observer { value ->
            value.apply {
                this?.nome = nomeTextInptEdit.text.toString()
                this?.telefone = telefoneTextInputEdit.text.toString()
                this?.endereco = enderecoInputEdit.text.toString()
                this?.matricula = matriculaTextInputEdit.text.toString()
                this?.cidade = cidadeInputEdit.text.toString()
                this?.uf = ufInputEdit.text.toString()
            }
        })

        viewModel.usuario.observe(this, Observer { value ->
            value.apply {
                emailTextView.setText(this?.email)
            }
        })

        return view
    }


    private fun salvar() {
        if (!isCamposObrigatoriosPreenchidos())
            return

        val progressDialog = dialogCarregando("Salvando dados do usuário")

        viewModel.updateValueAluno(Aluno().apply {
            this.nome = nomeTextInptEdit.text.toString()
            this.telefone = telefoneTextInputEdit.text.toString()
            this.endereco = enderecoInputEdit.text.toString()
            this.matricula = matriculaTextInputEdit.text.toString()
            this.cidade = cidadeInputEdit.text.toString()
            this.uf = ufInputEdit.text.toString()
        })

        viewModel.criarAluno(onComplete = {
            progressDialog.dismiss()
            longToast(getString(R.string.msg_aluno_sucesso))
        }, onError = { msg ->

            progressDialog.dismiss()
            msg?.let {
                alert {
                    message = it
                }.show()
            }

        })
    }


    private fun isCamposObrigatoriosPreenchidos(): Boolean {
        var isPreenchido = true

        campoPreenchido(nomeTextInputLaout,
                nomeTextInptEdit,
                mensagemCampoObrigatorio(R.string.nome)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(telefoneTextInputLayout,
                telefoneTextInputEdit,
                mensagemCampoObrigatorio(R.string.telefone)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(enderecoTextInputLayout,
                enderecoInputEdit,
                mensagemCampoObrigatorio(R.string.endereco)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(matriculaTextInputLayout,
                matriculaTextInputEdit,
                mensagemCampoObrigatorio(R.string.matricula)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(cidadeTextInputLayout,
                cidadeInputEdit,
                mensagemCampoObrigatorio(R.string.cidade)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(ufTextInputLayout,
                ufInputEdit,
                mensagemCampoObrigatorio(R.string.uf)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }


        return isPreenchido
    }

}
