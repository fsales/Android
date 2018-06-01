package com.androidi.fos.alunoonline.util

import java.util.regex.Pattern

private val REGEX_VALIDA_EMAIL = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"

fun validarEmail(email: String, callback: ((a: Boolean) -> Unit)): Boolean {
    val emailValido = Pattern.compile(REGEX_VALIDA_EMAIL).matcher(email).matches()

    callback(emailValido)

    return emailValido
}

