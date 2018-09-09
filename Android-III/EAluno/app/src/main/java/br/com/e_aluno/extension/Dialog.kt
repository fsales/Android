package br.com.e_aluno.extension

import br.com.e_aluno.EAlunoActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.indeterminateProgressDialog

fun EAlunoActivity.dialogErro(mensagem: String? = "Acontece um erro inesperado",
                              titulo: String? = "Atenção") {
    alert(Appcompat, mensagem!!) {
        title = titulo!!
    }.show()
}

fun EAlunoActivity.dialogInfo(mensagem: String,
                              titulo: String? = "Informação") =
        alert(Appcompat, mensagem!!) {
            title = titulo!!
        }.show()

fun EAlunoActivity.dialogCarregando(mensagem:String = "Processando!!")  = indeterminateProgressDialog(mensagem)