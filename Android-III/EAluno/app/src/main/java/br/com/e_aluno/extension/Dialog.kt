package br.com.e_aluno.extension

import android.support.v4.app.Fragment
import br.com.e_aluno.EAlunoActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.indeterminateProgressDialog

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

fun EAlunoActivity.dialogCarregando(mensagem: String? = "Carregando!!") = indeterminateProgressDialog(mensagem)

fun Fragment.dialogCarregando(mensagem: String? = "Carregando!!") = indeterminateProgressDialog(mensagem)