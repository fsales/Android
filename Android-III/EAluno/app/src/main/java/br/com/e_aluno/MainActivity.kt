package br.com.e_aluno

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import br.com.e_aluno.autenticacao.CadastarFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // replaceFragment(LoginFragment())

        button.setOnClickListener {
            button.visibility = View.GONE
            replaceFragment(CadastarFragment())

        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
           // addToBackStack()
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }
}
