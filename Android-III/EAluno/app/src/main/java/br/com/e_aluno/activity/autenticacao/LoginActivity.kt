package br.com.e_aluno.activity.autenticacao

import android.os.Bundle
import br.com.e_aluno.EAlunoActivity
import br.com.e_aluno.R
import br.com.e_aluno.extension.campoPreenchido
import br.com.e_aluno.extension.mensagemCampoObrigatorio
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : EAlunoActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonEntrar.setOnClickListener {
            signInWithEmailAndPassword()
        }

        esqueceuSenhaTxt.setOnClickListener {
            startActivity<RecuperarSenhaActivity>()
        }

        buttonCadastrar.setOnClickListener {
            startActivity<CadastrarActivity>()
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
                mensagemCampoObrigatorio(R.string.e_mail)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(senhaInputLayout,
                senhaTextInput,
                mensagemCampoObrigatorio(R.string.senha)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        return isPreenchido
    }
}
