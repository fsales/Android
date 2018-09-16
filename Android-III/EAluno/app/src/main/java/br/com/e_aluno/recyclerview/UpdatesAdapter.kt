package br.com.e_aluno.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.e_aluno.R
import br.com.e_aluno.model.ReviewUpdate
import br.com.e_aluno.model.Update

/*
class UpdatesAdapter(val context: Context,
                     var updatesList: List<Update>,
                     val listener: onItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        const val TYPE_FRIEND = 0
        const val TYPE_REVIEW = 1
    }

    override fun getItemViewType(position: Int): Int {
        val type = when (updatesList[position].updateType) {
            Update.TYPE.REVIEW -> TYPE_REVIEW
            // other types...
            else -> TYPE_FRIEND
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            TYPE_FRIEND -> ReviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_usuario, parent, false))
            else -> ReviewViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.card_usuario, parent, false))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UpdateViewHolder).bindViews(updatesList[position])
    }


    override fun getItemCount() = updatesList.size

    */
/*fun setUpdates(updates: List<Update>) {
        updatesList = updates
        notifyDataSetChanged()
    }*//*


    interface onItemClickListener {
        fun onUserNameOrImageClicked()
        fun onFriendNameOrImageClicked()
        // other listeners...
    }
}

interface UpdateViewHolder {
    fun bindViews(update: M)
}

// other view holders...

class ReviewViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView), UpdateViewHolder {

    override fun bindViews(update: Update) {
        val mUpdate = update as ReviewUpdate
    }
}

class ReviewViewHolder2(itemView: View)
    : RecyclerView.ViewHolder(itemView), UpdateViewHolder {

    override fun bindViews(update: Update) {
        val mUpdate = update as ReviewUpdate
    }
}*/
