package com.androidi.fos.alunoonline.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.androidi.fos.alunoonline.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnEsqueceuSenha.onClick {
            startActivity(Intent(this@Login, EsqueceuSenha::class.java))
        }

        btnCadastrar.onClick {

            startActivity(Intent(this@Login, CadastrarLogin::class.java))
        }
    }
}
