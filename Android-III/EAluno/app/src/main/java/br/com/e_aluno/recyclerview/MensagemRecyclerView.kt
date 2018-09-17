package br.com.e_aluno.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.e_aluno.R
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.Mensagem
import br.com.e_aluno.model.MensagemTexto
import br.com.e_aluno.model.TipoMensagem
import kotlinx.android.synthetic.main.item_mensagem_texto.view.*
import java.text.SimpleDateFormat

class MensagemRecyclerView(var list: List<Mensagem>? = listOf<Mensagem>())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        const val TYPE_TEXTO_DIREITA = 0
        const val TYPE_TEXTO_ESQUERDA = 1
    }


    override fun getItemCount(): Int = list?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        var type: Int? = 0

        list?.let { list ->

            val mensagem = list[position]

            if (TipoMensagem.TEXTO == mensagem.type &&
                    Auth.instance.uid() == mensagem.senderId) {
                type = TYPE_TEXTO_DIREITA
            } else if (TipoMensagem.TEXTO == mensagem.type &&
                    Auth.instance.uid() == mensagem.recipientId) {
                type = TYPE_TEXTO_ESQUERDA
            }
        }

        return type!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_TEXTO_DIREITA ->
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mensagem_texto_direita, parent, false))
        else ->
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mensagem_texto, parent, false))
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

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), BindViewHolder {

    var mensagemTexto: TextView
    var dataHoraMensagem: TextView

    init {
        this.mensagemTexto = itemView.mensagemTexto
        this.dataHoraMensagem = itemView.dataHoraMensagem
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
