package br.com.e_aluno.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.e_aluno.R
import br.com.e_aluno.firebase.Storage
import br.com.e_aluno.formataDataHora
import br.com.e_aluno.model.Noticia
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.card_noticia.view.*

class NoticiaRecyclerView(var list: List<Noticia>? = listOf<Noticia>(),
                          private val clickListener: (Noticia?) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_noticia, parent, false)

        return NoticiaViewHolder(view, clickListener)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        list?.let { list ->
            (holder as BindViewHolder<Noticia>).bindViews(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class NoticiaViewHolder(itemView: View,
                                  val clickListener: (Noticia?) -> Unit) : RecyclerView.ViewHolder(itemView), BindViewHolder<Noticia> {
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

        override fun bindViews(noticia: Noticia) {
            var data = ""
            noticia.dataNoticia?.let {
                data = formataDataHora(it)
            }
            this.title.setText(data + " - " + noticia.titulo)
            this.desc.setText(noticia.descricaoCurta)
            this.noticia = noticia

            noticia.imagemPath?.let { imagemPath ->
                if (imagemPath.isNotEmpty()) {
                    val options = RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.ic_image_black_24dp)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH)
                            .dontAnimate()
                            .dontTransform()

                    Glide.with(itemView.context)
                            .load(Storage.INSTANCE.pathToReference(imagemPath))
                            .apply(options)
                            .into(this.featuredImage)
                            .clearOnDetach()
                }
            }
        }
    }
}