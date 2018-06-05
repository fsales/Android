package com.androidi.fos.alunoonline.activity


import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem


abstract class AlunoOnLineBaseActivity() : AppCompatActivity() {

    /***
     * retorna verdadeiro se o campo obrigatorio for preenchido
     */
    protected fun validarCampoObrigatorio(textInputLayout: TextInputLayout?, textInputEditText: TextInputEditText?, mensagem: String) : Boolean {

        var isCampoPreenchido = true

        textInputEditText?.let {
            if (it.text.isNullOrEmpty()) {
                textInputLayout?.isErrorEnabled = true
                textInputLayout?.error = mensagem
                isCampoPreenchido = false
            } else {
                textInputLayout?.isErrorEnabled = false
            }
        }

        return isCampoPreenchido
    }

    protected fun getValor(textInputEditText: TextInputEditText?): String {
        return textInputEditText?.text.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }
    
}