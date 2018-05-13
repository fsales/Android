package com.androidi.fos.alunoonline.activity

import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity

abstract class AlunoOnLineBaseActivity() : AppCompatActivity() {

    protected fun validarCampoObrigatorio(textInputLayout: TextInputLayout?, textInputEditText: TextInputEditText?, mensagem: String) {
        textInputEditText?.let {
            if (it.text.isNullOrEmpty()) {
                textInputLayout?.isErrorEnabled = true
                textInputLayout?.error = mensagem
            } else {
                textInputLayout?.isErrorEnabled = false
            }
        }
    }
}