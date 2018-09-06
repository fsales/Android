package br.com.e_aluno.extension

import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import br.com.e_aluno.R

fun AppCompatActivity.campoPreenchido(textInputLayout: TextInputLayout?,
                                      textInputEditText: TextInputEditText?,
                                      mensagem: String,
                                      onSuccess: (preenchido: Boolean) -> Unit) {
    textInputEditText?.let {
        if (it.text.isNullOrEmpty()) {
            textInputLayout?.isErrorEnabled = true
            textInputLayout?.error = mensagem
            onSuccess(false)
        } else {
            textInputLayout?.isErrorEnabled = false
            onSuccess(true)
        }
    }
}

fun AppCompatActivity.mensagemCampoObrigatorio(resIdCampo: Int) = getString(R.string.template_msg_obrigatorio).format(getString(resIdCampo))

/*
fun uploadProfilePhoto(imageBytes: ByteArray,
                       onSuccess: (imagePath: String) -> Unit) {
    val ref = currentUserRef.child("profilePictures/${UUID.nameUUIDFromBytes(imageBytes)}")
    ref.putBytes(imageBytes).addOnSuccessListener {
        onSuccess(ref.path)
    }

}*/
