package com.androidi.fos.alunoonline.activity

import android.os.Bundle
import android.text.Editable
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.entity.Usuario
import com.androidi.fos.alunoonline.util.AlunoOnlineApplication
import kotlinx.android.synthetic.main.activity_usuario.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class AlterarUsuario : AlunoOnLineBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        val alunoOnLineAplication = application as? AlunoOnlineApplication

        // carregar dados do usuario logado
        alunoOnLineAplication?.let { alunoOnlineApplication ->

            alunoOnLineAplication.usuarioLogado?.let { usuario ->
                editTextEmail.text = Editable.Factory.getInstance().newEditable(usuario.email)

                usuario.matricula?.let { matricula ->
                    editTextMatricula.text = Editable.Factory.getInstance().newEditable(matricula.toString())
                }

                usuario.nome?.let { nome ->
                    editTextNome.text = Editable.Factory.getInstance().newEditable(nome)
                }

                usuario.telefone?.let { telefone ->
                    editTextTelefone.text = Editable.Factory.getInstance().newEditable(telefone)
                }


            }
        }

        // toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        btnConfirmar.onClick {
            validarCampoObrigatorio(textInputLayoutMatricula, editTextMatricula, getString(R.string.msg_matricula_obrigatorio))
            validarCampoObrigatorio(textInputLayoutNome, editTextNome, getString(R.string.msg_nome_obrigatorio))
            validarCampoObrigatorio(textInputLayoutEmail, editTextEmail, getString(R.string.msg_email_obrigatorio))
            validarCampoObrigatorio(textInputLayoutTelefone, editTextTelefone, getString(R.string.msg_telefone_obrigatorio))
            validarCampoObrigatorio(textInputLayoutSenha, editTextSenha, getString(R.string.msg_senha_obrigatorio))
            validarCampoObrigatorio(textInputLayoutConfirmarSenha, editTextConfirmarSenha, getString(R.string.msg_confirmar_senha_obrigatorio))



            if (!textInputLayoutMatricula.isErrorEnabled
                    && !textInputLayoutNome.isErrorEnabled
                    && !textInputLayoutEmail.isErrorEnabled
                    && !textInputLayoutTelefone.isErrorEnabled
                    && !textInputLayoutSenha.isErrorEnabled
                    && !textInputLayoutConfirmarSenha.isErrorEnabled
                    && validaConfirmacaoSenha()) {

                val usuario: Usuario = Usuario(
                        matricula = getValor(editTextMatricula).toInt(),
                        nome = getValor(editTextNome),
                        telefone = getValor(editTextTelefone),
                        email = getValor(editTextEmail),
                        senha = getValor(editTextSenha))

                alunoOnLineAplication?.let { alunoOnlineApplication ->
                    alunoOnLineAplication.usuarioLogado?.let {
                        usuario.uid = it.uid
                    }

                    alunoOnLineAplication.usuarioLogado = usuario
                }


                appDataBase()?.let { appDataBase ->

                    appDataBase.usuarioDAO().atualizar(usuario)
                }


            }

        }
    }

    fun validaConfirmacaoSenha() = if (editTextSenha.text.toString().equals(editTextConfirmarSenha.text.toString())) {
        textInputLayoutConfirmarSenha.isErrorEnabled = false
        true
    } else {
        textInputLayoutConfirmarSenha.isErrorEnabled = true
        textInputLayoutConfirmarSenha?.error = getString(R.string.msg_senha_diferente)
        false
    }
}
