package com.androidi.fos.alunoonline.activity

import android.os.Bundle
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.entity.Usuario
import kotlinx.android.synthetic.main.activity_cadastrar_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class CadastrarLogin : AlunoOnLineBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_login)

        btnConfirmar.onClick {

            validarCampoObrigatorio(textInputLayoutEmail, editTextEmail, getString(R.string.msg_email_obrigatorio))
            validarCampoObrigatorio(textInputLayoutSenha, editTextSenha, getString(R.string.msg_senha_obrigatorio))

            val senhaIguais = if (editTextEmail.text.toString().equals(editTextConfirmarSenha.text.toString())) {
                textInputLayoutConfirmacao.isErrorEnabled = false
                true
            } else {
                textInputLayoutConfirmacao.isErrorEnabled = true
                textInputLayoutConfirmacao?.error = "Senha diferente!"
                false
            }

            if (!textInputLayoutEmail.isErrorEnabled && !textInputLayoutSenha.isErrorEnabled && senhaIguais) {

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


}
