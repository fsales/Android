package br.com.e_aluno.model

import java.util.*

interface IMensagem {

    var dataHora: Date
    var senderId: String
    var recipientId: String
    var nome: String
    var type: String
}