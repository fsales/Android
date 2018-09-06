package br.com.e_aluno.activity.autenticacao

import android.os.Bundle
import br.com.e_aluno.EAlunoActivity
import br.com.e_aluno.R
import br.com.e_aluno.extension.*
import kotlinx.android.synthetic.main.activity_cadastrar.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.info
import org.jetbrains.anko.longToast

class CadastrarActivity : EAlunoActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }


        buttonConfirmar.setOnClickListener {
            cadastrar()
        }
        info("teste")
    }

    private fun cadastrar() {
        if (!isCamposObrigatoriosPreenchidos())
            return

        if (!senhaIguais())
            return

        if (!validarSenha())
            return
        if (!validarEmail())
            return
    }

    private fun validarEmail(): Boolean {
        var emailValido = true

        validarEmail(emailTextInputEdit.text.toString()) {
            emailValido = it
            if (emailValido == false) longToast(getString(R.string.email_invalido))
        }

        return emailValido
    }

    private fun validarSenha(): Boolean {
        var senhaValida = true

        validarSenha(senhaTextInputEdit.text.toString()) {
            senhaValida = it

            if (senhaValida == false) {
                alert(Appcompat, getString(R.string.senha_invalido)).show()
            }
        }

        return senhaValida
    }

    private fun senhaIguais(): Boolean {

        var senhaIguais = true
        confirmarSenhaTextInputLayout.isErrorEnabled = false

        camposIguais(senhaTextInputEdit, confirmarSenhaTextInputEdit) {
            senhaIguais = it

            if (!senhaIguais) {
                confirmarSenhaTextInputLayout.isErrorEnabled = true
                confirmarSenhaTextInputLayout?.error = getString(R.string.senha_diferente)
            }
        }

        return senhaIguais
    }

    private fun isCamposObrigatoriosPreenchidos(): Boolean {
        var isPreenchido = true

        campoPreenchido(emailTextInputLayout,
                emailTextInputEdit,
                mensagemCampoObrigatorio(R.string.e_mail)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(senhaTextInputLayout,
                senhaTextInputEdit,
                mensagemCampoObrigatorio(R.string.senha)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(confirmarSenhaTextInputLayout,
                confirmarSenhaTextInputEdit,
                mensagemCampoObrigatorio(R.string.confirmar_senha)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }


        return isPreenchido
    }
}
