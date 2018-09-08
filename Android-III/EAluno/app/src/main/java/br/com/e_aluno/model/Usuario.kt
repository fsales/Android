package br.com.e_aluno.model

import java.util.*

data class Usuario(var email: String,
                   var nome: String,
                   val caminhoFoto: String?,
                   val cricao: Date) {
    constructor() : this("", "", null, Date(0))
}