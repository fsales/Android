package br.com.e_aluno.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banner (var pathImagem:String?=  "") : Parcelable