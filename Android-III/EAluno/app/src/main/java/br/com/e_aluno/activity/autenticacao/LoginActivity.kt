package br.com.e_aluno.activity.autenticacao

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import br.com.e_aluno.EAlunoActivity
import br.com.e_aluno.MainActivity
import br.com.e_aluno.R
import br.com.e_aluno.extension.campoPreenchido
import br.com.e_aluno.extension.dialogCarregando
import br.com.e_aluno.extension.mensagemCampoObrigatorio
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.Usuario
import br.com.e_aluno.viewmodel.autenticacao.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*

class LoginActivity : EAlunoActivity() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

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

        viewModel.usuario.observe(this, Observer { value ->
            value.apply {
                emailTextInput.setText(this?.email)
                senhaTextInput.setText(this?.senha)
            }
        })
    }

    private fun signInWithEmailAndPassword() {
        if (!isCamposObrigatoriosPreenchidos())
            return

        val progressDialog = dialogCarregando("Autenticando!")



        viewModel.updateValueUsuario(Usuario().apply {
            this.email = emailTextInput.text.toString()
            this.senha = senhaTextInput.text.toString()
        })

        viewModel.signInWithEmailAndPassword(onComplete = {

            startActivity(intentFor<MainActivity>().newTask().clearTask())
            progressDialog.dismiss()
        }, onError = { msg ->
            progressDialog.dismiss()
            longToast(msg!!)
        })
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
