package br.com.e_aluno.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Noticia(var id: Int? = 0,
                   var titulo: String? = null,
                   @ServerTimestamp
                   @PropertyName("timestamp")
                   var dataNoticia: Date? = null,
                   var descricaoCurta: String? = null,
                   var descricao: String? = null,
                   var imagemPath: String? = null) : Parcelable