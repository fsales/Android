package br.com.e_aluno.fragment

import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import br.com.e_aluno.R
import br.com.e_aluno.activity.autenticacao.LoginActivity
import br.com.e_aluno.firebase.Auth
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask
import org.jetbrains.anko.support.v4.intentFor

abstract class MenuFragment : Fragment() {

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_toolbar, menu);

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.exit -> {
            Auth.instance.signOut()
            startActivity(intentFor<LoginActivity>().newTask().clearTask())
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }
}