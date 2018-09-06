package br.com.e_aluno.activity.autenticacao

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.e_aluno.R
import br.com.e_aluno.extension.campoPreenchido
import br.com.e_aluno.extension.mensagemCampoObrigatorio
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonEntrar.setOnClickListener {
            signInWithEmailAndPassword()
        }
    }

    private fun signInWithEmailAndPassword() {
        if (!isCamposObrigatoriosPreenchidos())
            return
    }

    private fun isCamposObrigatoriosPreenchidos(): Boolean {
        var isPreenchido = true

        campoPreenchido(emailInputLayout,
                emailTextInput,
                mensagemCampoObrigatorio(R.string.e_mail)) { preenchido -> isPreenchido = preenchido }

        campoPreenchido(senhaInputLayout,
                senhaTextInput,
                mensagemCampoObrigatorio(R.string.senha)) { preenchido -> isPreenchido = preenchido }

        return isPreenchido
    }
}
