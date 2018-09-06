package br.com.e_aluno

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import org.jetbrains.anko.AnkoLogger

abstract class EAlunoActivity : AppCompatActivity(), AnkoLogger {

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }
}