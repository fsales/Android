package com.androidi.fos.alunoonline.activity

import android.os.Bundle
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.entity.Usuario
import kotlinx.android.synthetic.main.activity_cadastrar_login.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class CadastrarLogin : AlunoOnLineBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_login)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        btnConfirmar.onClick {

            validarCampoObrigatorio(textInputLayoutEmail, editTextEmail, getString(R.string.msg_email_obrigatorio))
            validarCampoObrigatorio(textInputLayoutSenha, editTextSenha, getString(R.string.msg_senha_obrigatorio))
            validarCampoObrigatorio(textInputLayoutConfirmacao, editTextSenhaConfirmacao, getString(R.string.confirmar_senha_obrigatorio))



            if (!textInputLayoutConfirmacao.isErrorEnabled && !textInputLayoutEmail.isErrorEnabled && !textInputLayoutSenha.isErrorEnabled && validaConfirmacaoSenha()) {

                val usuario = Usuario(email = editTextEmail.text.toString().toLowerCase(), senha = editTextSenha.text.toString())

                appDataBase()?.let {

                    val usuarioExistente = it.usuarioDAO().getUsuario(usuario.email!!)

                    if (!usuarioExistente?.email.isNullOrBlank()) {
                        toast("E-mail já cadastrado!")
                    } else {
                        it.usuarioDAO().incluir(usuario)
                        toast("Usuário Cadastrado com sucesso!")
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
        textInputLayoutConfirmacao?.error = "Senha diferente!"
        false
    }

}
