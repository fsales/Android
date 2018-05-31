package com.androidi.fos.alunoonline.activity


import android.content.Context
import android.os.Handler
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import org.jetbrains.anko.indeterminateProgressDialog


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


    fun load(delay: Long = 1000) {
        val a = indeterminateProgressDialog("Processando...").apply {
            title = "Aluno On Line"
            max = delay.toInt()
            setCancelable(false)

        }

        a.show()

        Handler().postDelayed({
            a.dismiss()
        }, delay)
    }

}