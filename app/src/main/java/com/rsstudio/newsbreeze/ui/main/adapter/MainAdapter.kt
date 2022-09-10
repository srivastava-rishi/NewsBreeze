package com.rsstudio.newsbreeze.ui.main.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.makeramen.roundedimageview.RoundedImageView
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.data.network.model.ArticleData
import de.hdodenhof.circleimageview.CircleImageView

class MainAdapter(
    private var context: Context,
    private var listener: MainAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<ArticleData> = mutableListOf()

    var logTag = "@MainAdapter"

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var tvDate: TextView = view.findViewById(R.id.tvDate)
        var tvTitle: TextView = view.findViewById(R.id.tvTitle)
        var tvContent: TextView = view.findViewById(R.id.tvContent)
        var ivNewsImage: ImageView = view.findViewById(R.id.ivNewsImage)
        var rlSavedNews: RelativeLayout = view.findViewById(R.id.rlSaved)
        var rlUnsavedNews: RelativeLayout = view.findViewById(R.id.rlSaved)
        var rlRead: RelativeLayout = view.findViewById(R.id.rlRead)

        var container: RelativeLayout = view.findViewById(R.id.rlRoot)

        fun onBind(item: ArticleData, position: Int) {

            tvTitle.text = item.title
            tvContent.text = item.description
            tvContent.text = item.description

            rlRead.setOnClickListener {
                listener.onReadClicked(item)
            }

            // setting image
            Glide
                .with(context)
                .load(item.urlToImage)
                .thumbnail(0.7f)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(ivNewsImage)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_news_view, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = list[position]
        if (holder is MainAdapter.ItemViewHolder) {
            holder.container.animation = AnimationUtils.loadAnimation(context,R.anim.anim_fade_scale)
            holder.onBind(item, position)
        }
    }

    fun submitList(newList: List<ArticleData>) {
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

    interface MainAdapterListener {
        fun onReadClicked(item: ArticleData)
    }
}