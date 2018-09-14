package br.com.e_aluno.fragment.alunos


import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.e_aluno.R
import br.com.e_aluno.extension.campoPreenchido
import br.com.e_aluno.extension.dialogCarregando
import br.com.e_aluno.extension.mensagemCampoObrigatorio
import br.com.e_aluno.firebase.Storage
import br.com.e_aluno.fragment.MenuFragment
import br.com.e_aluno.model.Aluno
import br.com.e_aluno.viewmodel.aluno.AlunoViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_aluno.*
import kotlinx.android.synthetic.main.fragment_aluno.view.*
import kotlinx.android.synthetic.main.fragment_noticias.view.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast
import java.io.ByteArrayOutputStream


class AlunoFragment : MenuFragment() {

    private val RC_SELECT_IMAGE = 2
    private var imagemSelecionadaBytes: ByteArray? = null

    private val viewModel: AlunoViewModel by lazy {
        ViewModelProviders.of(this).get(AlunoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_aluno, container, false)

        viewModel.aluno.observe(this, Observer { value ->
            value.apply {
                nomeTextInptEdit.setText(this?.nome)
                telefoneTextInputEdit.setText(this?.telefone)
                enderecoInputEdit.setText(this?.endereco)
                matriculaTextInputEdit.setText(this?.matricula)
                cidadeInputEdit.setText(this?.cidade)
                ufInputEdit.setText(this?.uf)
            }
        })
        val context = this.context
        viewModel.usuario.observe(this, Observer { value ->
            value.apply {
                emailTextView.setText(this?.email)

                this?.caminhoFoto?.let { caminhoFoto ->
                    if (caminhoFoto.isNotEmpty()) {
                      /*  val options = RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.ic_add_a_photo_black_24dp)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform()
            */
                       Glide.with(context!!).load(Storage.INSTANCE.pathToReference(caminhoFoto)).into(fotoImageView)
                    }
                }

            }
        })


        setHasOptionsMenu(true);
        view.run {

            val activityAppCompatActivity = (activity as? AppCompatActivity)
            activityAppCompatActivity?.let {
                it.setSupportActionBar(toolbar)

            }

            this.confirmarBotton.setOnClickListener {
                salvar()
            }

            fotoImageView.setOnClickListener {
                fotoOnClick()
            }
        }

        val progress = dialogCarregando()

        viewModel.carregarDadosUsuario(onComplete = {
            viewModel.carregarDadosAluno(onComplete = {
                progress.dismiss()
            }, onErro = {
                progress.dismiss()
            })
        }, onErro = { exception ->
            progress.dismiss()
        })


        return view
    }


    private fun salvar() {
        if (!isCamposObrigatoriosPreenchidos())
            return

       val progress = dialogCarregando("Salvando dados do usuário")

        viewModel.updateValueAluno(Aluno().apply {
            this.nome = nomeTextInptEdit.text.toString()
            this.telefone = telefoneTextInputEdit.text.toString()
            this.endereco = enderecoInputEdit.text.toString()
            this.matricula = matriculaTextInputEdit.text.toString()
            this.cidade = cidadeInputEdit.text.toString()
            this.uf = ufInputEdit.text.toString()
        })

        viewModel.criarAluno(imagemSelecionadaBytes,
                onComplete = {
            progress.dismiss()
            longToast(getString(R.string.msg_aluno_sucesso))
        }, onError = { msg ->

            progress.dismiss()
            msg?.let {
                alert {
                    message = it
                }.show()
            }

        })
    }


    private fun isCamposObrigatoriosPreenchidos(): Boolean {
        var isPreenchido = true

        campoPreenchido(nomeTextInputLaout,
                nomeTextInptEdit,
                mensagemCampoObrigatorio(R.string.nome)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(telefoneTextInputLayout,
                telefoneTextInputEdit,
                mensagemCampoObrigatorio(R.string.telefone)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(enderecoTextInputLayout,
                enderecoInputEdit,
                mensagemCampoObrigatorio(R.string.endereco)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(matriculaTextInputLayout,
                matriculaTextInputEdit,
                mensagemCampoObrigatorio(R.string.matricula)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(cidadeTextInputLayout,
                cidadeInputEdit,
                mensagemCampoObrigatorio(R.string.cidade)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }

        campoPreenchido(ufTextInputLayout,
                ufInputEdit,
                mensagemCampoObrigatorio(R.string.uf)) { preenchido -> if (preenchido == false) isPreenchido = preenchido }


        return isPreenchido
    }

    private fun fotoOnClick() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
        }
        startActivityForResult(Intent.createChooser(intent, "Select Image"), RC_SELECT_IMAGE)
    }

    private fun carregarImagemGaleria(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
                data != null && data.data != null) {

            val caminhoImagemSelecionada = data.data
            val imagemSelecionada = MediaStore.Images.Media.getBitmap(activity?.contentResolver, caminhoImagemSelecionada)

            val outputStream = ByteArrayOutputStream()
            imagemSelecionada.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            imagemSelecionadaBytes = outputStream.toByteArray()
            Glide.with(this).load(imagemSelecionada).into(fotoImageView)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        carregarImagemGaleria(requestCode, resultCode, data)

    }
}
