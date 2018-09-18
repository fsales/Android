package br.com.e_aluno.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MensagemImagem(var imagemPath: String) : Mensagem(
        Date(0),
        "",
        "",
        "",
        TipoMensagem.IMAGEM), Parcelable {
    constructor() : this("")
}