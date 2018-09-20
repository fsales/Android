package br.com.e_aluno

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

fun transformeBitmap(rDrawable: Int): ByteArray {
    val bitmap = (rDrawable    as? BitmapDrawable)?.bitmap
    val stream = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.PNG, 90, stream)
    return stream.toByteArray()
}


fun solicitarPermissao(contexto: Context,
                       activity: Activity,
                       listaPermisao: ArrayList<String>,
                       requestPermission: Int) {
    listaPermisao.forEach {
        val isPermitido = ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED
        if (!isPermitido) {
            ActivityCompat.requestPermissions(activity, listaPermisao.toTypedArray(), requestPermission)
        }
    }
}

fun permissoesConcedidas(contexto: Context,
                         activity: Activity,
                         listaPermisao: ArrayList<String>,
                         onComplete: (listaPermisaoConcedidas: List<String>) -> Unit) {

    val listaPermisaoConcedidas = arrayListOf<String>()
    listaPermisao.forEach {
        val isPermitido = ContextCompat.checkSelfPermission(contexto, it) == PackageManager.PERMISSION_GRANTED
        if (isPermitido) listaPermisaoConcedidas.add(it)
    }
    onComplete(listaPermisaoConcedidas)
}

fun dialogPermissao(activity: Activity,
                    titulo: String,
                    listaOpcao: Array<CharSequence>,
                    onComplete: (dialog: DialogInterface?, which: Int) -> Unit) {
    val getImageFrom = AlertDialog.Builder(activity).setTitle(titulo)
    getImageFrom.setItems(listaOpcao) { dialog, which ->
        onComplete(dialog, which)
        dialog.dismiss()
    }
    getImageFrom.show()

}

fun formataDataHora(data: Date): String {
    val format = SimpleDateFormat("dd/MM/yyy - HH:mm:ss ")

    return format.format(data)
}