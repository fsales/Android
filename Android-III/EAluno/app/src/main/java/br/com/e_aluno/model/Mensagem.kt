package br.com.e_aluno.model

import java.util.*

abstract class Mensagem(override var dataHora: Date,
                        override var senderId: String,
                        override var recipientId: String,
                        override var nome: String,
                        override val type: String) : IMensagem