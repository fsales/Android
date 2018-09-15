package br.com.e_aluno.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class MensagemTexto(var texto: String,
                         override var dataHora: Date,
                         override var senderId: String,
                         override var recipientId: String,
                         override var nome: String,
                         override var type: String) : IMensagem, Parcelable {

    constructor() : this("", Date(0), "", "", "", "")
}