package br.com.e_aluno.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Aluno(var uuid: String? = null,
                 var nome: String? = "",
                 var matricula: String? = "",
                 var telefone: String? = "",
                 var endereco: String? = "",
                 var cidade: String? = "",
                 var uf: String? = "",
                 var uuidUsuario: String? = null) : Parcelable