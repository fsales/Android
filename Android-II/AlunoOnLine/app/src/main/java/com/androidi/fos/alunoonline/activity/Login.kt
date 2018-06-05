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

    var alunoOnLineAplication: AlunoOnlineApplication? = null
    var mAuth: FirebaseAuth? = null
    //var appDataBase: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        alunoOnLineAplication = application as? AlunoOnlineApplication

        mAuth = FirebaseAuth.getInstance()

       /* alunoOnLineAplication?.let { alOnLineAplication ->
            appDataBase = alOnLineAplication.appDataBase()
        }*/

        buttonEntrar.onClick {

            validarCampoObrigatorio(textInputLayoutEmail, editTextEmail, getString(R.string.msg_email_obrigatorio))
            validarCampoObrigatorio(textInputLayoutSenha, editTextSenha, getString(R.string.msg_senha_obrigatorio))

            if (!textInputLayoutEmail.isErrorEnabled && !textInputLayoutSenha.isErrorEnabled) {

                load()

                val email = editTextEmail.text.toString().toLowerCase()
                val senha = editTextSenha.text.toString()

                mAuth?.let { m ->

                    m.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            val intent = Intent(this@Login, Home::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        }else{
                            longToast("Usuário ou Senha inválido!")
                        }
                    }
                }

                /*appDataBase?.let {
                    val usuario = it.usuarioDAO().getUsuario(email, senha)

                    if (usuario == null) {
                        longToast("Usuário ou Senha inválido!")
                    }


                    usuario?.let {

                        alunoOnLineAplication?.let { alOnlineApplication ->
                            alOnlineApplication.usuarioLogado = Usuario(uid = usuario.uid)
                        }

                        Handler().postDelayed({
                            val intent = Intent(this@Login, Home::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        }, 700)


                    }
                }*/
            }


        }

        btnEsqueceuSenha.onClick {
            startActivity(Intent(this@Login, EsqueceuSenha::class.java))
        }

        btnCadastrar.onClick {

            startActivity(Intent(this@Login, CadastrarLogin::class.java))
        }
    }
}
