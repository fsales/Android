package br.com.e_aluno.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.e_aluno.R
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.firebase.Storage
import br.com.e_aluno.model.Mensagem
import br.com.e_aluno.model.MensagemImagem
import br.com.e_aluno.model.MensagemTexto
import br.com.e_aluno.model.TipoMensagem
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import kotlinx.android.synthetic.main.item_mensagem_texto.view.*
import java.text.SimpleDateFormat

class MensagemRecyclerView(var list: List<Mensagem>? = listOf<Mensagem>())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        const val TYPE_TEXTO_DIREITA = 0
        const val TYPE_TEXTO_ESQUERDA = 1
        const val TYPE_IMAGEM_DIREITA = 2
        const val TYPE_IMAGEM_ESQUERDA = 3
    }


    override fun getItemCount(): Int = list?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        var type: Int? = 0

        list?.let { list ->

            val mensagem = list[position]

            type = when {
                TipoMensagem.TEXTO == mensagem.type &&
                        Auth.instance.uid() == mensagem.senderId -> TYPE_TEXTO_DIREITA
                TipoMensagem.TEXTO == mensagem.type &&
                        Auth.instance.uid() == mensagem.recipientId -> TYPE_TEXTO_ESQUERDA
                TipoMensagem.IMAGEM == mensagem.type &&
                        Auth.instance.uid() == mensagem.senderId -> TYPE_IMAGEM_DIREITA
                TipoMensagem.IMAGEM == mensagem.type &&
                        Auth.instance.uid() == mensagem.recipientId -> TYPE_IMAGEM_ESQUERDA
                else -> {
                    TYPE_IMAGEM_ESQUERDA
                }
            }
        }

        return type!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_IMAGEM_DIREITA ->
            MensagemImagemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mensagem_imagem_direita, parent, false))
        TYPE_IMAGEM_ESQUERDA ->
            MensagemImagemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mensagem_imagem_esquerda, parent, false))
        TYPE_TEXTO_DIREITA ->
            MensagemTextoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mensagem_texto_direita, parent, false))
        else ->
            MensagemTextoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mensagem_texto_esquerda, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        list?.let { list ->
            (holder as BindViewHolder).bindViews(list[position])
        }
    }

    fun setUpdates(updates: List<Mensagem>) {
        list = updates
        notifyDataSetChanged()
    }
}

interface BindViewHolder {
    fun bindViews(mensagem: Mensagem)
}

class MensagemTextoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), BindViewHolder {

    var mensagemTexto: TextView
    var dataHoraMensagem: TextView

    init {
        this.mensagemTexto = itemView.findViewById(R.id.mensagemTexto)
        this.dataHoraMensagem = itemView.findViewById(R.id.dataHoraMensagem)
    }

    override fun bindViews(mensagem: Mensagem) {

        val msgTexto = mensagem as? MensagemTexto

        msgTexto?.let { msgTexto ->

            itemView.apply {
                this.mensagemTexto.setText(msgTexto.texto)
                val format = SimpleDateFormat("dd/MM/yyy - HH:mm:ss ")
                this.dataHoraMensagem.setText(format.format(msgTexto.dataHora))
            }
        }


    }
}

class MensagemImagemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), BindViewHolder {

    var imagemView: ImageView
    var dataHoraMensagem: TextView

    init {
        this.imagemView = itemView.findViewById(R.id.imagemView)
        this.dataHoraMensagem = itemView.findViewById(R.id.dataHoraMensagem)
    }

    override fun bindViews(mensagem: Mensagem) {

        val msgTexto = mensagem as? MensagemImagem

        msgTexto?.let { msgTexto ->

            itemView.apply {

                val format = SimpleDateFormat("dd/MM/yyy - HH:mm:ss ")
                this.dataHoraMensagem.setText(format.format(msgTexto.dataHora))

                if (msgTexto.imagemPath.isNotEmpty()) {

                    val options = RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_image_black_24dp)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH)
                            .dontAnimate()
                            .dontTransform()

                    Glide.with(context!!)
                            .load(Storage.INSTANCE.pathToReference(msgTexto.imagemPath))
                            .apply(options)
                            .into(imagemView)
                            .clearOnDetach()
                }
            }

        }


    }
}
