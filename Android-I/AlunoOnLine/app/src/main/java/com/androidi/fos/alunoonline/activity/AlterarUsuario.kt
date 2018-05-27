package com.androidi.fos.alunoonline.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.entity.Usuario
import com.androidi.fos.alunoonline.util.AlunoOnlineApplication
import kotlinx.android.synthetic.main.activity_usuario.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.ByteArrayOutputStream

class AlterarUsuario : AlunoOnLineBaseActivity() {

    private val USER_PROFILE_CAMERA_REQUEST_CODE = 9995
    private val USER_PROFILE_GALLERY_REQUEST_CODE = 9996
    private var imageUri: Uri? = null

    companion object {
        const val REQUEST_PERMISSION = 1
    }

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

        imgFoto.setOnClickListener {

            val isPermissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            val isPermissionReadExtenalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            val isPermissionWriteExtenalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

            if (!isPermissionCamera || !isPermissionReadExtenalStorage || !isPermissionWriteExtenalStorage) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION)
            } else {
                getImage()
            }

        }

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

                longToast("Usuário alterado com sucesso!")
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

    private fun getImage() {
        val getImageFrom = AlertDialog.Builder(this)
        getImageFrom.setTitle("Foto Produto")
        val opsChars = arrayOf<CharSequence>("Câmera", "Galeria")
        getImageFrom.setItems(opsChars) { dialog, which ->
            if (which == 0) {

                val values = ContentValues()
                values.put(MediaStore.Images.Media.TITLE, "Foto Usuário")
                values.put(MediaStore.Images.Media.DESCRIPTION, "Câmera")

                imageUri = contentResolver
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values)
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (intent.resolveActivity(packageManager) != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                            or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }

                startActivityForResult(intent, USER_PROFILE_CAMERA_REQUEST_CODE)
            } else if (which == 1) {
                val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                pickIntent.type = "image/*"
                startActivityForResult(pickIntent, USER_PROFILE_GALLERY_REQUEST_CODE)
            }
            dialog.dismiss()
        }
        getImageFrom.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            val imageUriTemp = if (requestCode == USER_PROFILE_GALLERY_REQUEST_CODE) {
                data?.data
            } else {
                imageUri
            }

            imageUriTemp?.let {
                imgFoto.setImageURI(it)
            }

        }
    }

    private fun convertImageToByte(uri: Uri): ByteArray {
        var data: ByteArray? = null

        val cr = baseContext.contentResolver
        val inputStream = cr.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        data = baos.toByteArray()

        return data
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_PERMISSION -> {
                if (permissions.contains(Manifest.permission.CAMERA) && grantResults.contains(PackageManager.PERMISSION_GRANTED)
                        && permissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResults.contains(PackageManager.PERMISSION_GRANTED)
                        && permissions.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                    getImage()
                }
            }
        }
    }
}
