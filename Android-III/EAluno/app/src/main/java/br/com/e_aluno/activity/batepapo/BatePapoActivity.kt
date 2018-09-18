package br.com.e_aluno.activity.batepapo

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import br.com.e_aluno.*
import br.com.e_aluno.extension.dialogErro
import br.com.e_aluno.firebase.firestone.ChatFirestore
import br.com.e_aluno.fragment.alunos.AlunoFragment
import br.com.e_aluno.model.Mensagem
import br.com.e_aluno.model.MensagemImagem
import br.com.e_aluno.model.MensagemTexto
import br.com.e_aluno.model.Usuario
import br.com.e_aluno.recyclerview.MensagemRecyclerView
import br.com.e_aluno.viewmodel.chat.BatePapoViewModel
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_bate_papo.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.ByteArrayOutputStream
import java.util.*

class BatePapoActivity : EAlunoActivity() {

    private val viewModel: BatePapoViewModel by lazy {
        ViewModelProviders.of(this).get(BatePapoViewModel::class.java)
    }

    private val listaPermissaoFoto: ArrayList<String> by lazy {
        arrayListOf(Manifest.permission.CAMERA)
    }

    private lateinit var mensagemListener: ListenerRegistration
    private lateinit var idCanalCorente: String
    private lateinit var adapter: MensagemRecyclerView

    private val RC_SELECT_CAMERA = 1
    private val RC_SELECT_IMAGE = 2
    private var imageUri: Uri? = null

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

        viewModel.mesagens.observe(this, android.arch.lifecycle.Observer { list ->
            list?.let {
                adapter.list = it
                adapter?.notifyDataSetChanged();
                recyclerView.scrollToPosition(recyclerView.adapter.itemCount - 1)
            }
        })


        imageViewEnviar.setOnClickListener {
            enviarMensagemTexto()
        }

        imagemButton.setOnClickListener {
            enviarMensagemImagem()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MensagemRecyclerView()
        this.recyclerView.adapter = adapter
    }

    private fun criarChate(uidOtherUsuario: String) {
        ChatFirestore.instance.chat(uidOtherUsuario) { canalId ->
            idCanalCorente = canalId
            mensagemListener = ChatFirestore.instance
                    .addChatMensagemListener(canalId, this, this::updateRecyclerView)
        }
    }

    private fun updateRecyclerView(mensagens: ArrayList<Mensagem>) {

        viewModel.mesagens.value = mensagens
    }

    private fun enviarMensagemTexto() {

        if (editTextMensagem.text.isEmpty())
            return

        viewModel.mensagem.value = MensagemTexto().apply {
            this.texto = editTextMensagem.text.toString()
            this.dataHora = Calendar.getInstance().time
            this.senderId = usuarioAtual?.uuid!!
            this.nome = usuarioAtual?.email!!.substringBefore("@")
            this.recipientId = uidOtherUsuario!!
        }

        viewModel.enviarMensagemTexto(idCanalCorente, onComplete = {
            editTextMensagem.setText("")
        }, onError = {
            dialogErro(it)
        })


    }

    private fun enviarMensagemImagem() {
        solicitarPermissao(this, this, listaPermissaoFoto, 1)
        capturarFoto(this, this, listaPermissaoFoto)
    }

    private fun capturarFoto(context: Context,
                             activity: Activity,
                             permissao: ArrayList<String>) {
        permissoesConcedidas(context!!, activity, permissao, onComplete = {
            if (it.size == permissao.size) {

                dialogPermissao(activity, "Fotos do usuáro", arrayOf<CharSequence>("Câmera", "Galeria"), onComplete = { dialog, which ->
                    if (which == 0)
                        cameraIntent(RC_SELECT_CAMERA, packageManager = context.packageManager, contentResolver = context.contentResolver)
                    else
                        fotoIntent(RC_SELECT_IMAGE)
                })
            }
        })
    }

    private fun cameraIntent(requestCode: Int,
                             packageManager: PackageManager,
                             contentResolver: ContentResolver) {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "Foto Usuário")
            put(MediaStore.Images.Media.DESCRIPTION, "Câmera")
        }

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent().apply {
            action = MediaStore.ACTION_IMAGE_CAPTURE
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            flags.plus(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }

        startActivityForResult(intent, RC_SELECT_CAMERA)
    }

    private fun fotoIntent(requestCode: Int) {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
        }
        startActivityForResult(Intent.createChooser(intent, "Select Image"), requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data?.data == null && imageUri != null) {
            data?.data = imageUri
        }
        carregarImagem(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            AlunoFragment.REQUEST_PERMISSION -> {
                capturarFoto(this, this, listaPermissaoFoto)
            }
        }


    }

    private fun carregarImagem(requestCode: Int, resultCode: Int, data: Intent?) {

        if ((requestCode == RC_SELECT_IMAGE || requestCode == RC_SELECT_CAMERA) &&
                resultCode == Activity.RESULT_OK &&
                data != null && data.data != null) {

            val caminhoImagemSelecionada = data.data


            val imagemSelecionada = MediaStore.Images.Media.getBitmap(this.contentResolver, caminhoImagemSelecionada)

            val outputStream = ByteArrayOutputStream()
            imagemSelecionada.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)

            viewModel.mensagemImagem.value = MensagemImagem().apply {
                this.dataHora = Calendar.getInstance().time
                this.senderId = usuarioAtual?.uuid!!
                this.nome = usuarioAtual?.email!!.substringBefore("@")
                this.recipientId = uidOtherUsuario!!
            }

            viewModel.enviarMensagemImagem(idCanalCorente, imagemBytes = outputStream, onComplete = {

            }, onError = {
                dialogErro(it)
            })
        }
    }


}
