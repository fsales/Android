package br.com.e_aluno

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import java.io.ByteArrayOutputStream

fun transformeBitmap(rDrawable: Int): ByteArray {
    val bitmap = (rDrawable    as? BitmapDrawable)?.bitmap
    val stream = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.PNG, 90, stream)
    return stream.toByteArray()
}