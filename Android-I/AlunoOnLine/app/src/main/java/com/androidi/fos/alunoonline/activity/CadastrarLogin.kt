package com.androidi.fos.alunoonline.activity

import android.content.Intent
import android.os.Bundle
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.db.AppDataBase
import com.androidi.fos.alunoonline.entity.Usuario
import com.androidi.fos.alunoonline.util.AlunoOnlineApplication
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

            validarCampoObrigatorio(textInputLayoutEmail, editTextEmail, getString(R.string.msg_email_obrigatorio))
            validarCampoObrigatorio(textInputLayoutSenha, editTextSenha, getString(R.string.msg_senha_obrigatorio))
            validarCampoObrigatorio(textInputLayoutConfirmacao, editTextSenhaConfirmacao, getString(R.string.msg_confirmar_senha_obrigatorio))



            if (!textInputLayoutConfirmacao.isErrorEnabled && !textInputLayoutEmail.isErrorEnabled && !textInputLayoutSenha.isErrorEnabled && validaConfirmacaoSenha()) {

                val usuario = Usuario(email = editTextEmail.text.toString().toLowerCase(), senha = editTextSenha.text.toString())

                appDataBase?.let {

                    val usuarioExistente = it.usuarioDAO().getUsuario(usuario.email!!)

                    if (!usuarioExistente?.email.isNullOrBlank()) {
                        longToast(getString(R.string.msg_email_ja_cadastrado))

                    } else {
                        it.usuarioDAO().incluir(usuario)
                        longToast(getString(R.string.msg_usuario_cadastrado_sucesso))

                        alunoOnLineAplication?.let { alOnlineApplication ->
                            alOnlineApplication.usuarioLogado = usuario
                        }

                        val intent = Intent(this@CadastrarLogin, Home::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
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
