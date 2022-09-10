package com.rsstudio.newsbreeze.ui.fullnews

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.data.network.model.ArticleData
import com.rsstudio.newsbreeze.databinding.ActivityFullNewsBinding
import com.rsstudio.newsbreeze.ui.base.BaseActivity
import com.rsstudio.newsbreeze.util.Constant

class FullNewsActivity : BaseActivity() , View.OnClickListener {

    private lateinit var binding: ActivityFullNewsBinding

    var item: ArticleData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_news)

        //
        initData()
        initAction()
        initViews()
    }

    private fun initData() {
        item = intent.getSerializableExtra(Constant.PARAM_NEWS) as? ArticleData
    }

    private fun initAction() {
        binding.ivBack.setOnClickListener(this)
    }

    private fun initViews() {

        binding.tvTitle.text = item!!.title
        binding.tvContent.text = item!!.content
        binding.tvName.text = item!!.source.name
        binding.tvName.text = item!!.author
        binding.tvSource.text = item!!.source.name
        binding.tvDescription.text = item!!.description

        // setting image

        Glide.with(this).load(item!!.urlToImage).into(object :
            CustomTarget<Drawable>() {
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable>?
            ) {
                binding.rlBb.background= resource
            }

        })

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.ivBack -> {
              finish()
            }

        }
    }
}

