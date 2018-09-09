package br.com.e_aluno.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Usuario(var email: String? = "",
                   var senha: String? = "",
                   var nome: String? = "",
                   var caminhoFoto: String? = "",
                   @ServerTimestamp var cadastro: Timestamp? = null)