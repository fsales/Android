package br.com.e_aluno.model

import java.util.*


object TipoMensagem {
    const val TEXTO = "TEXTO"
    const val IMAGEM = "IMAGEM"
}

interface IMensagem {

    var dataHora: Date
    var senderId: String
    var recipientId: String
    var nome: String
    val type: String
}