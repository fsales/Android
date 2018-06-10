package com.androidi.fos.alunoonline.activity


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.db.AppDataBase
import com.androidi.fos.alunoonline.entity.Usuario
import com.androidi.fos.alunoonline.extension.load
import com.androidi.fos.alunoonline.util.AlunoOnlineApplication
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk25.coroutines.onClick

class Login : AlunoOnLineBaseActivity() {

    var mAuth: FirebaseAuth? = null

    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        buttonEntrar.onClick {
            signInWithEmailAndPassword()
        }

        btnEsqueceuSenha.onClick {
            startActivity(Intent(this@Login, EsqueceuSenha::class.java))
        }

        btnCadastrar.onClick {

            startActivity(Intent(this@Login, CadastrarLogin::class.java))
        }
    }

    private fun signInWithEmailAndPassword() {
        if (!formValido()) {
            return
        }

        load()

        val email = editTextEmail.text.toString().toLowerCase()
        val senha = editTextSenha.text.toString()

        mAuth?.let { m ->

            val signIn = m.signInWithEmailAndPassword(email, senha)
            signIn.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@Login, Home::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                } else {
                    longToast(getString(R.string.msg_usuario_senha_invalido))
                }
            }
        }
    }

    private fun formValido(): Boolean {
        val emailValido = validarCampoObrigatorio(textInputLayoutEmail, editTextEmail, getString(R.string.msg_email_obrigatorio))
        val senhaValido = validarCampoObrigatorio(textInputLayoutSenha, editTextSenha, getString(R.string.msg_senha_obrigatorio))

        return emailValido && senhaValido
    }
}
