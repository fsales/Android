package br.com.e_aluno.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.e_aluno.R
import br.com.e_aluno.firebase.Storage
import br.com.e_aluno.model.Usuario
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.card_usuario.view.*

class AlunoRecyclerView(var list: List<Usuario>? = listOf<Usuario>(),
                        private val context: Context,
                        private val clickListerner: (Usuario) -> Unit) : RecyclerView.Adapter<AlunoRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_usuario, parent, false)

        return ViewHolder(view)
    }

    fun on(func: () -> Unit) {

    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        list?.let {
            val usuario = it[position] as Usuario
            holder.email.text = usuario.email

            usuario.caminhoFoto?.let { caminhoFoto ->

                val options = RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.ic_account_circle_black_24dp)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .dontAnimate()
                        .dontTransform()
                val caminhoFotoStorage = Storage.INSTANCE.pathToReference(caminhoFoto)

                Glide.with(context)
                        .load(caminhoFotoStorage)
                        .apply(options)
                        .into(holder.featuredImage)

            }

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

            itemView.setOnClickListener {
                clickListerner(Usuario().apply {
                    this.email = email
                })
            }
        }
    }
}

