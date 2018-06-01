package com.androidi.fos.alunoonline.util

import java.util.regex.Pattern

private val REGEX_VALIDA_EMAIL = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"

fun validarEmail(email: String, callback: ((emailValido: Boolean) -> Unit)): Boolean {
    val emailValido = Pattern.compile(REGEX_VALIDA_EMAIL).matcher(email).matches()

    callback(emailValido)

    return emailValido
}

fun validarSenha(senha: String, callback: ((senhaValido: Boolean) -> Unit)): Boolean {

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

