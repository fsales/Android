package com.androidi.fos.alunoonline.entity

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class Teste(var nome: String? = "Casa") : Serializable

class Noticia(var id: Int? = 0,
              var titulo: String? = null,
              var dataNoticia: String? = SimpleDateFormat("dd/MM/yyyy").format(Date()),
              var descricaoCurta: String? = null,
              var descricao: String? = null,
              var codigoImagem: Int? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(titulo)
        parcel.writeString(dataNoticia)
        parcel.writeString(descricaoCurta)
        parcel.writeString(descricao)
        parcel.writeValue(codigoImagem)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun imagem(resource: Resources): Bitmap? {

        codigoImagem?.let {
            return BitmapFactory.decodeResource(resource, it)
        }
        return null
    }

    companion object CREATOR : Parcelable.Creator<Noticia> {
        override fun createFromParcel(parcel: Parcel): Noticia {
            return Noticia(parcel)
        }

        override fun newArray(size: Int): Array<Noticia?> {
            return arrayOfNulls(size)
        }
    }
}