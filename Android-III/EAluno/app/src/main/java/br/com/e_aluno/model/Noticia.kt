package br.com.e_aluno.model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Noticia(var id: Int? = 0,
                   var titulo: String? = null,
                   var dataNoticia: String? = SimpleDateFormat("dd/MM/yyyy").format(Date()),
                   var descricaoCurta: String? = null,
                   var descricao: String? = null,
                   var codigoImagem: Int? = null) : Parcelable {

    fun imagem(resource: Resources): Bitmap? {

        codigoImagem?.let {
            return BitmapFactory.decodeResource(resource, it)
        }
        return null
    }
}