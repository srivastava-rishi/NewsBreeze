package com.rsstudio.newsbreeze.ui.savedNews.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.data.local.entity.SavedNewsEntity
import com.rsstudio.newsbreeze.data.network.model.ArticleData


class SavedNewsAdapter(
    private var context: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<SavedNewsEntity> = mutableListOf()

    var logTag = "@MainAdapter"

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var tvDate: TextView = view.findViewById(R.id.tvDate)
        var tvTitle: TextView = view.findViewById(R.id.tvTitle)
        var tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        var rivNewsImage: ImageView = view.findViewById(R.id.rivNewsImage)


        var container: RelativeLayout = view.findViewById(R.id.rlRoot)

        @SuppressLint("UseCompatLoadingForColorStateLists")
        fun onBind(item: SavedNewsEntity, position: Int) {

            tvDate.text = item.title

            if (item.author != null) {
                tvAuthor.text = item.author
            }else{
                tvAuthor.text = "Default Author"
            }


            // setting image
            Glide
                .with(context)
                .load(item.urlToImage)
                .thumbnail(0.7f)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(rivNewsImage)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_saved_news, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = list[position]
        if (holder is SavedNewsAdapter.ItemViewHolder) {
            holder.container.animation = AnimationUtils.loadAnimation(context, R.anim.anim_fade_scale)
            holder.onBind(item, position)
        }
    }

    fun submitList(newList: List<SavedNewsEntity>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        if (list.size != 0) {
            return list.size
        }
        return 0
    }

}