package br.com.e_aluno.activity.batepapo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.e_aluno.EAlunoActivity
import br.com.e_aluno.R
import kotlinx.android.synthetic.main.toolbar.*

class BatePapoActivity : EAlunoActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bate_papo)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }


    }
}
