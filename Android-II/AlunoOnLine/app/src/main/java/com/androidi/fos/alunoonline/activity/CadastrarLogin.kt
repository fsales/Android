package com.androidi.fos.alunoonline.activity

import android.content.Intent
import android.os.Bundle
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.entity.Usuario
import com.androidi.fos.alunoonline.extension.load
import com.androidi.fos.alunoonline.util.FirebaseAuthError
import com.androidi.fos.alunoonline.util.validarEmail
import com.androidi.fos.alunoonline.util.validarSenha
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.activity_cadastrar_login.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk25.coroutines.onClick

class CadastrarLogin : AlunoOnLineBaseActivity() {

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_login)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        mAuth = FirebaseAuth.getInstance()

        btnConfirmar.onClick {
            createUserWithEmailAndPassword()
        }
    }

    private fun createUserWithEmailAndPassword() {
        if (!formValido()) {
            return
        }

        load()

        val usuario = Usuario(email = editTextEmail.text.toString().toLowerCase(), senha = editTextSenha.text.toString())

        mAuth?.let { mAuth ->

            val criarUsuario = mAuth.createUserWithEmailAndPassword(usuario.email!!, usuario.senha!!)
            criarUsuario.addOnCompleteListener({ task ->

                try {
                    if (task.isSuccessful) {
                        longToast(getString(R.string.msg_usuario_cadastrado_sucesso))
                        val intent = Intent(this@CadastrarLogin, Home::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }

                    msgErro(task.exception)
                } catch (firebaseAuthUserException: FirebaseAuthUserCollisionException) {
                    msgErro(firebaseAuthUserException)
                }
            })
        }
    }

    private fun msgErro(exception: Exception?) {

        exception?.let { ex ->
            var msgError = FirebaseAuthError.ERROR_UNKNOWN.description

            when (ex) {
                is FirebaseAuthUserCollisionException -> {
                    val firebaseAuthError = FirebaseAuthError.fromException(ex)
                    msgError = firebaseAuthError.description
                }
            }

            longToast(msgError)
        }
    }

    private fun formValido(): Boolean {


        val emailPreenchido = validarCampoObrigatorio(textInputLayoutEmail, editTextEmail, getString(R.string.msg_email_obrigatorio))
        val senhaPreenchido = validarCampoObrigatorio(textInputLayoutSenha, editTextSenha, getString(R.string.msg_senha_obrigatorio))
        val confirmacaoSenhaPreenchido = validarCampoObrigatorio(textInputLayoutConfirmacao, editTextSenhaConfirmacao, getString(R.string.msg_confirmar_senha_obrigatorio))

        val isEmailValido = validarEmail(editTextEmail.text.toString()) { emalValido ->
            if (emalValido == false && emailPreenchido) longToast(getString(R.string.msg_email_invalido))
        }

        val isSenhaValida = validarSenha(editTextSenha.text.toString()) { senhaValida ->
            if (senhaValida == false && senhaPreenchido) {
                longToast(getString(R.string.msg_senha_invalido))
            }
        }

        return emailPreenchido && senhaPreenchido && confirmacaoSenhaPreenchido && isEmailValido && isSenhaValida && validaConfirmacaoSenha()
    }

    private fun validaConfirmacaoSenha() = if (editTextSenha.text.toString().equals(editTextSenhaConfirmacao.text.toString())) {
        textInputLayoutConfirmacao.isErrorEnabled = false
        true
    } else {
        textInputLayoutConfirmacao.isErrorEnabled = true
        textInputLayoutConfirmacao?.error = getString(R.string.msg_senha_diferente)
        false
    }

}
