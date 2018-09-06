package br.com.e_aluno.extension

import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import br.com.e_aluno.R
import java.util.regex.Pattern

private val REGEX_VALIDA_EMAIL = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"

fun AppCompatActivity.validarEmail(email: String, callback: ((emailValido: Boolean) -> Unit)): Boolean {
    val emailValido = Pattern.compile(REGEX_VALIDA_EMAIL).matcher(email).matches()

    callback(emailValido)

    return emailValido
}

fun AppCompatActivity.validarSenha(senha: String, callback: ((senhaValido: Boolean) -> Unit)): Boolean {

    var isConteinsUpperCase = false
    var isConteinsCaracterEspecial = false
    var isConteinsDigito = false

    senha.forEach {
        if (it.isUpperCase()) {
            isConteinsUpperCase = true
        }

        if (!it.isDigit() && !it.isLetter()) {
            isConteinsCaracterEspecial = true
        }

        if (it.isDigit()) {
            isConteinsDigito = true
        }
    }

    val senhaValida = senha.length >= 6 && isConteinsCaracterEspecial && isConteinsDigito && isConteinsUpperCase

    callback(senhaValida)

    return senhaValida
}



fun AppCompatActivity.campoPreenchido(textInputLayout: TextInputLayout?,
                                      textInputEditText: TextInputEditText?,
                                      mensagem: String,
                                      onSuccess: (preenchido: Boolean) -> Unit) {
    textInputEditText?.let {
        if (it.text.isNullOrEmpty()) {
            textInputLayout?.isErrorEnabled = true
            textInputLayout?.error = mensagem
            onSuccess(false)
        } else {
            textInputLayout?.isErrorEnabled = false
            onSuccess(true)
        }
    }
}

fun AppCompatActivity.mensagemCampoObrigatorio(resIdCampo: Int) = getString(R.string.template_msg_obrigatorio).format(getString(resIdCampo))

fun AppCompatActivity.camposIguais(textInputEditText1: TextInputEditText,
                                   textInputEditText2: TextInputEditText,
                                   onSuccess: (iguais: Boolean) -> Unit) {

    val camposIguais = textInputEditText1.text.toString().equals(textInputEditText2.text.toString())
    onSuccess(camposIguais)

}
