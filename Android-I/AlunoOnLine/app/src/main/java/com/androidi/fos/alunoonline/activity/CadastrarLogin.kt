package com.androidi.fos.alunoonline.activity

import android.os.Bundle
import com.androidi.fos.alunoonline.R
import kotlinx.android.synthetic.main.activity_cadastrar_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class CadastrarLogin : AlunoOnLineBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_login)
        btnConfirmar.onClick {
            validarCampoObrigatorio(textInputLayoutEmail, editTextEmail, getString(R.string.msg_email_obrigatorio))
            validarCampoObrigatorio(textInputLayoutSenha, editTextSenha, getString(R.string.msg_senha_obrigatorio))
        }
    }


}
