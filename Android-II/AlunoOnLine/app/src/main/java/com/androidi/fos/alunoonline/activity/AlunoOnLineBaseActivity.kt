package com.androidi.fos.alunoonline.activity


import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ProgressBar
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.util.FirebaseAuthError
import org.jetbrains.anko.AnkoLogger
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.jetbrains.anko.longToast


abstract class AlunoOnLineBaseActivity() : AppCompatActivity(), AnkoLogger {

    private var mProgressBar: ProgressBar? = null

    fun showProgressBar(mensagem:String = getString(R.string.msg_processando)){

       // progressbar

        mProgressBar = ProgressBar(this)
        mProgressBar
    }

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

    protected fun msgErro(exception: Exception?) {

        exception?.let { ex ->
            var msgError = FirebaseAuthError.ERROR_UNKNOWN.description

            when (ex) {
                is FirebaseAuthException -> {
                    val firebaseAuthError = FirebaseAuthError.fromException(ex)
                    msgError = firebaseAuthError.description
                }
            }

            longToast(msgError)
        }
    }
    
}
