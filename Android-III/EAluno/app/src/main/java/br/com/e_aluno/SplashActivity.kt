package br.com.e_aluno

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.e_aluno.activity.autenticacao.LoginActivity
import br.com.e_aluno.firebase.Auth
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Auth.instance.currentUser() == null) {
            startActivity(intentFor<LoginActivity>().newTask().clearTask())
        } else {
            startActivity(intentFor<MainActivity>().newTask().clearTask())
        }
    }
}