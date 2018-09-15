package br.com.e_aluno.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.e_aluno.R
import br.com.e_aluno.model.Noticia
import kotlinx.android.synthetic.main.card_noticia.view.*
import org.jetbrains.anko.imageBitmap

class NoticiaRecyclerView(private val list: List<Noticia>,
                          private val clickListener: (Noticia?) -> Unit) : RecyclerView.Adapter<NoticiaRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_noticia, parent, false)

        return ViewHolder(view, clickListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noticia = list[position] as Noticia

        holder.title.text = noticia.dataNoticia + " - " + noticia.titulo
        holder.desc.text = noticia.descricaoCurta
        holder.featuredImage.imageBitmap = noticia.imagem(holder.itemView.context.resources)
        holder.noticia = noticia

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View,
                           val clickListener: (Noticia?) -> Unit) : RecyclerView.ViewHolder(itemView) {

        var featuredImage: ImageView
        var title: TextView
        var desc: TextView

        var noticia: Noticia? = null

        init {


            this.featuredImage = itemView.featureImage
            this.title = itemView.titulo
            this.desc = itemView.desc

            this.itemView.setOnClickListener {
                clickListener(noticia)
            }
        }
    }
}