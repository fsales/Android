package br.com.e_aluno.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class MensagemTexto(var texto: String) : Mensagem(
        Date(0), "", "", "", TipoMensagem.TEXTO), Parcelable {

    constructor() : this("")
}