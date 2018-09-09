package br.com.e_aluno

import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
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

    override fun finish() {
        super.finish()
       // overridePendingTransition(R.anim.mover_esquerda, R.anim.abc_fade_out)
    }

    fun startActivityAnin(context: Context, intent: Intent) {
        val anim = ActivityOptionsCompat.makeCustomAnimation(applicationContext, R.anim.fade_in, R.anim.mover_direita)
        ActivityCompat.startActivity(context, intent, anim.toBundle())
    }
}