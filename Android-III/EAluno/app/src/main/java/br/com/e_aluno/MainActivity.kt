package br.com.e_aluno

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import br.com.e_aluno.fragment.alunos.AlunoFragment
import br.com.e_aluno.fragment.alunos.ListarAlunosFragment
import br.com.e_aluno.fragment.noticias.ListarNoticiasFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.fragmento.observe(this, Observer {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_layout, it)
                    .commit()
        })

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_noticias -> {
                    viewModel.onNavigationItemSelected(ListarNoticiasFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_alunos -> {

                    viewModel.onNavigationItemSelected(ListarAlunosFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_minha_conta -> {
                    viewModel.onNavigationItemSelected(AlunoFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    internal class MainActivityViewModel() : ViewModel() {

        val fragmento: MutableLiveData<Fragment> by lazy {
            MutableLiveData<Fragment>().apply {
                value = ListarNoticiasFragment()
            }
        }

        fun onNavigationItemSelected(fragment: Fragment) {
            fragmento.value = fragment
        }
    }
}
