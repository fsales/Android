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

class AlunoRecyclerView(private var list: List<Usuario>) : RecyclerView.Adapter<AlunoRecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_usuario, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = list[position] as Usuario

        holder.nome.text = usuario.nome
        holder.email.text = usuario.email

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var featuredImage: ImageView
        var nome: TextView
        var email: TextView

        init {


            this.featuredImage = itemView.imageView_profile_picture
            this.nome = itemView.textView_name
            this.email = itemView.textView_email
        }
    }
}

