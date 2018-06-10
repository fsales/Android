package com.androidi.fos.alunoonline.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.db.AppDataBase
import com.androidi.fos.alunoonline.entity.Usuario
import com.androidi.fos.alunoonline.extension.load
import com.androidi.fos.alunoonline.util.AlunoOnlineApplication
import com.androidi.fos.alunoonline.util.validarEmail
import com.androidi.fos.alunoonline.util.validarSenha
import kotlinx.android.synthetic.main.activity_cadastrar_login.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk25.coroutines.onClick

class CadastrarLogin : AlunoOnLineBaseActivity() {

    var alunoOnLineAplication: AlunoOnlineApplication? = null
    var appDataBase: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_login)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        alunoOnLineAplication = application as? AlunoOnlineApplication

        alunoOnLineAplication?.let { alOnLineAplication ->
            appDataBase = alOnLineAplication.appDataBase()
        }

        btnConfirmar.onClick {

            val emailPreenchido = validarCampoObrigatorio(textInputLayoutEmail, editTextEmail, getString(R.string.msg_email_obrigatorio))
            val senhaPreenchido = validarCampoObrigatorio(textInputLayoutSenha, editTextSenha, getString(R.string.msg_senha_obrigatorio))
            val confirmacaoSenhaPreenchido = validarCampoObrigatorio(textInputLayoutConfirmacao, editTextSenhaConfirmacao, getString(R.string.msg_confirmar_senha_obrigatorio))

            val isEmailValido = validarEmail(editTextEmail.text.toString()) { emalValido ->
                if (emalValido == false && emailPreenchido) longToast(getString(R.string.msg_email_invalido))
            }

            val isSenhaValida = validarSenha(editTextSenha.text.toString()) { senhaValida ->
                if (senhaValida == false && senhaPreenchido) {
                    longToast(getString(R.string.msg_senha_invalido))
                }
            }

            val isSalvaDados = emailPreenchido && senhaPreenchido && confirmacaoSenhaPreenchido && isEmailValido && isSenhaValida && validaConfirmacaoSenha()

            if (isSalvaDados) {

                load()

                val usuario = Usuario(email = editTextEmail.text.toString().toLowerCase(), senha = editTextSenha.text.toString())

                appDataBase?.let {

                    val usuarioExistente = it.usuarioDAO().getUsuario(usuario.email!!)

                    if (!usuarioExistente?.email.isNullOrBlank()) {
                        longToast(getString(R.string.msg_email_ja_cadastrado))

                    } else {
                        it.usuarioDAO().incluir(usuario)
                        longToast(getString(R.string.msg_usuario_cadastrado_sucesso))

                        val usuarioCadastrado = it.usuarioDAO().getUsuario(usuario.email!!)

                        usuarioCadastrado?.let { usuarioCadastrado ->
                            alunoOnLineAplication?.let { alOnlineApplication ->
                                alOnlineApplication.usuarioLogado = Usuario(uid = usuarioCadastrado.uid)
                            }
                        }



                        Handler().postDelayed({
                            val intent = Intent(this@CadastrarLogin, Home::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        }, 700)


                    }


                }
            }


        }
    }

    fun validaConfirmacaoSenha() = if (editTextSenha.text.toString().equals(editTextSenhaConfirmacao.text.toString())) {
        textInputLayoutConfirmacao.isErrorEnabled = false
        true
    } else {
        textInputLayoutConfirmacao.isErrorEnabled = true
        textInputLayoutConfirmacao?.error = getString(R.string.msg_senha_diferente)
        false
    }

}
