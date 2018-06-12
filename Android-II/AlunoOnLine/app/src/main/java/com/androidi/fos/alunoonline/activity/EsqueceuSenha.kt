package com.androidi.fos.alunoonline.activity

import android.os.Bundle
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.extension.load
import com.androidi.fos.alunoonline.util.validarEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.activity_esqueceu_senha.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk25.coroutines.onClick

class EsqueceuSenha : AlunoOnLineBaseActivity() {

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esqueceu_senha)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        mAuth = FirebaseAuth.getInstance()

        btnConfirmar.onClick {
            resetPassword()
        }

    }

    private fun resetPassword() {
        if (!formValido()) {
            return
        }

        load()

        mAuth?.let { mAuth ->

            try {
                val passwordReset = mAuth.sendPasswordResetEmail(textEditEmail.text.toString())
                passwordReset.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        longToast(String.format("Enviado para o endereço %s, um e-mail com as informações de recuperação.", textEditEmail.text.toString()))
                    }

                    msgErro(task.exception)
                }
            } catch (firebaseAuthUserException: FirebaseAuthUserCollisionException) {
                msgErro(firebaseAuthUserException)
            }


        }
    }


    private fun formValido(): Boolean {
        val isEmailPreenchido = validarCampoObrigatorio(textInputLayoutEmail, textEditEmail, getString(R.string.msg_email_obrigatorio))

        val isEmailValido = validarEmail(textEditEmail.text.toString()) { email ->
            if (email == false && isEmailPreenchido) longToast(getString(R.string.msg_email_invalido))
        }

        return isEmailPreenchido && isEmailValido
    }
}
