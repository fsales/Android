package br.com.e_aluno.activity.batepapo

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import br.com.e_aluno.AppContantes
import br.com.e_aluno.EAlunoActivity
import br.com.e_aluno.R
import br.com.e_aluno.extension.dialogErro
import br.com.e_aluno.firebase.firestone.ChatFirestore
import br.com.e_aluno.model.IMensagem
import br.com.e_aluno.model.MensagemTexto
import br.com.e_aluno.model.Usuario
import br.com.e_aluno.viewmodel.chat.BatePapoViewModel
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_bate_papo.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class BatePapoActivity : EAlunoActivity() {

    private val viewModel: BatePapoViewModel by lazy {
        ViewModelProviders.of(this).get(BatePapoViewModel::class.java)
    }

    private lateinit var mensagemListener: ListenerRegistration
    private lateinit var idCanalCorente: String

    private var uidOtherUsuario: String? = null
    private var usuarioAtual: Usuario? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bate_papo)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        // view model

        viewModel.otherUsuario.value = intent.extras.getParcelable(AppContantes.INTENT_USUARIO)


        viewModel.otherUsuario.observe(this, android.arch.lifecycle.Observer { usuario ->
            usuario?.let {
                uidOtherUsuario = it.uuid
                supportActionBar?.title = it.email
                criarChate(it.uuid!!)
            }
        })

        viewModel.usuarioCorrente.observe(this, android.arch.lifecycle.Observer { usuario ->
            usuarioAtual = usuario
        })


        imageViewEnviar.setOnClickListener {
            enviarMensagem()
        }

    }

    private fun criarChate(uidOtherUsuario: String) {
        ChatFirestore.instance.chat(uidOtherUsuario) { canalId ->
            idCanalCorente = canalId
            mensagemListener = ChatFirestore.instance
                    .addChatMensagemListener(canalId, this, this::updateRecyclerView)
        }
    }

    private fun updateRecyclerView(messages: List<IMensagem>) {

        Log.d("FIRESTORE", "ChatMessagesListener error.")
    }

    private fun enviarMensagem() {

        if (editTextMensagem.text.isEmpty())
            return

        viewModel.mensagem.value = MensagemTexto().apply {
            this.texto = editTextMensagem.text.toString()
            this.dataHora = Calendar.getInstance().time
            this.senderId = usuarioAtual?.uuid!!
            this.nome = usuarioAtual?.email!!.substringBefore("@")
            this.recipientId = uidOtherUsuario!!
        }

        viewModel.enviarMensagem(idCanalCorente, onError = {
            dialogErro(it)
        })


    }
}
