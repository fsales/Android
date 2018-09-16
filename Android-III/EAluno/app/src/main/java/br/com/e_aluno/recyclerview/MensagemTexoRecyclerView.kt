package br.com.e_aluno.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.e_aluno.R
import br.com.e_aluno.model.IMensagem
import br.com.e_aluno.model.MensagemTexto
import kotlinx.android.synthetic.main.item_mensagem_texto.view.*
import java.text.SimpleDateFormat

class MensagemTexoRecyclerView(var list: List<IMensagem>? = listOf<IMensagem>()) : RecyclerView.Adapter<MensagemTexoRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensagem_texto, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        var qtd = 0
        if (list != null) {
            qtd = list?.size!!
        }

        return qtd
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        list?.let { it ->

            val mensagem = it[position] as? MensagemTexto
            mensagem?.let {
                holder.mensagemTexto.setText(it.texto)
                val format = SimpleDateFormat("dd/MM/yyy - HH:mm:ss ")
                holder.dataHoraMensagem.setText(format.format(mensagem.dataHora))
            }


        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mensagemTexto: TextView
        var dataHoraMensagem: TextView

        init {
            this.mensagemTexto = itemView.mensagemTexto
            this.dataHoraMensagem = itemView.dataHoraMensagem
        }
    }

}
