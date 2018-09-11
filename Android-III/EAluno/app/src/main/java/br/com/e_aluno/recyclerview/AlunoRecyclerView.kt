package br.com.e_aluno.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.e_aluno.R
import br.com.e_aluno.model.Usuario
import kotlinx.android.synthetic.main.card_usuario.view.*

class AlunoRecyclerView(var list: List<Usuario>? = listOf<Usuario>()) : RecyclerView.Adapter<AlunoRecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_usuario, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        list?.let {
            val usuario = it[position] as Usuario
            holder.nome.text = usuario.nome
            holder.email.text = usuario.email
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var featuredImage: ImageView
        var nome: TextView
        var email: TextView

        init {
            this.featuredImage = itemView.imageView_profile_picture
            this.email = itemView.textView_email
            this.nome = itemView.textView_name
        }
    }
}

