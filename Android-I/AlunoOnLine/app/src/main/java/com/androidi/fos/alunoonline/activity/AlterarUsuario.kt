package com.androidi.fos.alunoonline.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.util.Base64
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.db.AppDataBase
import com.androidi.fos.alunoonline.entity.Usuario
import com.androidi.fos.alunoonline.util.AlunoOnlineApplication
import kotlinx.android.synthetic.main.activity_usuario.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.ByteArrayOutputStream

class AlterarUsuario : AlunoOnLineBaseActivity() {

    private val USER_PROFILE_CAMERA_REQUEST_CODE = 9995
    private val USER_PROFILE_GALLERY_REQUEST_CODE = 9996
    private var imageUri: Uri? = null
    var appDataBase: AppDataBase? = null
    var alunoOnLineAplication: AlunoOnlineApplication? = null

    companion object {
        const val REQUEST_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        alunoOnLineAplication = application as? AlunoOnlineApplication


        // carregar dados do usuario logado
        alunoOnLineAplication?.let { alOnlineApplication ->
            appDataBase = alOnlineApplication.appDataBase()

            alOnlineApplication.usuarioLogado?.let { usuTemp ->
                carregarDadoUsuarioTela(usuTemp.uid)
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
                        senha = getValor(editTextSenha)
                        )

                alunoOnLineAplication?.usuarioLogado?.uid?.let {
                    usuario.uid = it
                }

                val bitmapDrawable = imgFoto?.drawable as? BitmapDrawable
                bitmapDrawable?.let {
                    usuario.fotoBase64 = convertImageToStringBase64(it.bitmap)
                }

                appDataBase?.let { appDataBase ->

                    appDataBase.usuarioDAO().atualizar(usuario)
                }

                longToast("Usuário alterado com sucesso!")
            }

        }
    }

    private fun carregarDadoUsuarioTela(uid: Int) {
        var usuario: Usuario? = null

        appDataBase?.let { appDataBase ->
            val usu = appDataBase.usuarioDAO().getUsuario(uid)
            usu?.let {
                usuario = usu
            }
        }

        usuario?.let {

            editTextEmail.text = Editable.Factory.getInstance().newEditable(it.email)

            it.matricula?.let { matricula ->
                editTextMatricula.text = Editable.Factory.getInstance().newEditable(matricula.toString())
            }

            it.nome?.let { nome ->
                editTextNome.text = Editable.Factory.getInstance().newEditable(nome)
            }

            it.telefone?.let { telefone ->
                editTextTelefone.text = Editable.Factory.getInstance().newEditable(telefone)
            }

            it.fotoBase64?.let {
                if (it.isNotEmpty()) {
                    imgFoto.imageBitmap = convetStringBase64ToBitMap(it)
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

    private fun convertImageToStringBase64(bitMap: Bitmap?): String? {
        var dataString: String? = null

        bitMap?.let {
            val baos = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 60, baos)
            dataString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
        }

        return dataString
    }


    private fun convetStringBase64ToBitMap(imgBase64: String?): Bitmap? {
        var imgBitMap: Bitmap? = null
        imgBase64?.let {
            val decodedString = Base64.decode(it, Base64.DEFAULT)
            imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }

        return imgBitMap
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
