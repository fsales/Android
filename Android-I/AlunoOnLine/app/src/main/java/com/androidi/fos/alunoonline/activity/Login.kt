package com.androidi.fos.alunoonline.activity

import android.content.Intent
import android.os.Bundle
import com.androidi.fos.alunoonline.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class Login : AlunoOnLineBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonEntrar.onClick {

            validarCampoObrigatorio(textInputLayoutEmail, editTextEmail, getString(R.string.msg_email_obrigatorio))
            validarCampoObrigatorio(textInputLayoutSenha, editTextSenha, getString(R.string.msg_senha_obrigatorio))

            if (!textInputLayoutEmail.isErrorEnabled && !textInputLayoutSenha.isErrorEnabled) {
                val email = editTextEmail.text.toString().toLowerCase()
                val senha = editTextSenha.text.toString()
                appDataBase()?.let {
                    val usuario = it.usuarioDAO().getUsuario(email, senha)

                    if (usuario == null) {
                        toast("Usuário ou Senha inválido!")
                    }


                    usuario?.let {
                        val intent = Intent(this@Login, Home::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                }
            }


        }

        btnEsqueceuSenha.onClick {
            startActivity(Intent(this@Login, EsqueceuSenha::class.java))
        }

        btnCadastrar.onClick {

            startActivity(Intent(this@Login, CadastrarLogin::class.java))
        }
    }
}
