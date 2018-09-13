package br.com.e_aluno.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Aluno(var nome: String? = "",
                 var matricula: String? = "",
                 var telefone: String? = "",
                 var endereco: String? = "",
                 var cidade: String? = "",
                 var uf: String? = "",
                 var usuario: Usuario? = null) : Parcelable