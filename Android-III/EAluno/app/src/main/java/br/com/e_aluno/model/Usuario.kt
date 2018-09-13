package br.com.e_aluno.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Usuario(var email: String? = "",
                   var senha: String? = "",
                   var nome: String? = "",
                   var caminhoFoto: String? = "",
                   @ServerTimestamp var cadastro: Date? = null) : Parcelable