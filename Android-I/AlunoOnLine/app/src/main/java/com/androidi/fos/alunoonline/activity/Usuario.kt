package com.androidi.fos.alunoonline.activity

import android.os.Bundle
import com.androidi.fos.alunoonline.R
import kotlinx.android.synthetic.main.activity_usuario.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class Usuario : AlunoOnLineBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        btnConfirmar.onClick {
            validarCampoObrigatorio(textInputLayoutMatricula, editTextMatricula, getString(R.string.msg_matricula_obrigatorio))
            validarCampoObrigatorio(textInputLayoutNome, editTextNome, getString(R.string.msg_nome_obrigatorio))
            validarCampoObrigatorio(textInputLayoutEmail, editTextEmail, getString(R.string.msg_email_obrigatorio))
            validarCampoObrigatorio(textInputLayoutTelefone, editTextTelefone, getString(R.string.msg_telefone_obrigatorio))
            validarCampoObrigatorio(textInputLayoutSenha, editTextSenha, getString(R.string.msg_senha_obrigatorio))
            validarCampoObrigatorio(textInputLayoutConfirmarSenha, editTextConfirmarSenha, getString(R.string.msg_confirmar_senha_obrigatorio))
        }
    }
}
