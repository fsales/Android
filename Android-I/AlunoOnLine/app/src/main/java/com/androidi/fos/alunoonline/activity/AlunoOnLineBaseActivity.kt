package com.androidi.fos.alunoonline.activity

import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.androidi.fos.alunoonline.db.AppDataBase

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

    protected fun appDataBase(): AppDataBase? {
        AppDataBase?.let {
            return it.getDatabase(context = applicationContext)
        }
        return null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}