package br.com.e_aluno.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Canal(val idsUsuario: MutableList<String?>) : Parcelable {
    constructor() : this(mutableListOf())
}