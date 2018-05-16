package com.androidi.fos.alunoonline.entity

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

class Noticia(var id: Int? = 0,
              var titulo: String? = null,
              var dataNoticia: String? = SimpleDateFormat("dd/MM/yyyy").format(Date()),
              var descricaoCurta: String? = null,
              var descricao: String? = null,
              var imagem: Bitmap? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Bitmap::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(titulo)
        parcel.writeString(dataNoticia)
        parcel.writeString(descricaoCurta)
        parcel.writeString(descricao)
        parcel.writeParcelable(imagem, flags)
    }

    override fun describeContents(): Int {
        return 0
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