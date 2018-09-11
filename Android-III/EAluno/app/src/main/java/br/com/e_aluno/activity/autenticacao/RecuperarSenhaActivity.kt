package br.com.e_aluno.activity.autenticacao

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import br.com.e_aluno.EAlunoActivity
import br.com.e_aluno.R
import br.com.e_aluno.extension.campoPreenchido
import br.com.e_aluno.extension.dialogCarregando
import br.com.e_aluno.extension.mensagemCampoObrigatorio
import br.com.e_aluno.extension.validarEmail
import br.com.e_aluno.model.Usuario
import br.com.e_aluno.viewmodel.autenticacao.RecuperarSenhaViewModel
import kotlinx.android.synthetic.main.activity_recuperar_senha.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.longToast

class RecuperarSenhaActivity : EAlunoActivity() {

    private val viewModel: RecuperarSenhaViewModel by lazy {
        ViewModelProviders.of(this).get(RecuperarSenhaViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        buttonRecuperar.setOnClickListener {
            recuperarSenha()
        }

    }

    private fun recuperarSenha() {
        if (!isCamposObrigatoriosPreenchidos())
            return

        if (!validarEmail())
            return

        val progressDialog = dialogCarregando("Recuperando senha!")

        viewModel.updateValueUsuario(Usuario().apply {
            this.email = emailTextInputEdit.text.toString()
        })

        viewModel.sendPasswordResetEmail(onComplete = {
            progressDialog.dismiss()
            longToast(it)
        }, onError = {
            progressDialog.dismiss()
            longToast(it!!)
        })
    }

    private fun validarEmail(): Boolean {
        var emailValido = true

        validarEmail(emailTextInputEdit.text.toString()) {
            emailValido = it
            if (emailValido == false) longToast(getString(R.string.email_invalido))
        }

        return emailValido
    }

    private fun isCamposObrigatoriosPreenchidos(): Boolean {
        var isPreenchido = true

        campoPreenchido(emailTextInputLayout,
                emailTextInputEdit,
                mensagemCampoObrigatorio(R.string.e_mail)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }
        return isPreenchido
    }
}
