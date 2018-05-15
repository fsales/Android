package com.androidi.fos.alunoonline.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.androidi.fos.alunoonline.R
import com.androidi.fos.alunoonline.entity.Noticia
import kotlinx.android.synthetic.main.card_noticia.view.*
import org.jetbrains.anko.imageBitmap
import java.text.SimpleDateFormat
import java.util.*


class RecyclerViewNoticia(private val list: List<Noticia>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_noticia, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noticia = list[position]

        holder.title.text =  SimpleDateFormat("dd/MM/yyyy").format(noticia.dataNoticia) + " - " + noticia.titulo
        holder.desc.text = noticia.descricaoCurta
        holder.featuredImage.imageBitmap = noticia.imagem
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var featuredImage: ImageView
    var title: TextView
    var desc: TextView
    var btnLink: Button

    init {


        this.featuredImage = itemView.featureImage
        this.title = itemView.titulo
        this.desc = itemView.desc
        this.btnLink = itemView.btn_link
    }
}