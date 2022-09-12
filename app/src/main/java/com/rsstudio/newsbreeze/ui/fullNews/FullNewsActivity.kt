package com.rsstudio.newsbreeze.ui.fullNews


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.data.local.database.SavedNewsDatabase
import com.rsstudio.newsbreeze.data.local.entity.SavedNewsEntity
import com.rsstudio.newsbreeze.data.local.helper.SavedNewsHelper
import com.rsstudio.newsbreeze.data.network.model.ArticleData
import com.rsstudio.newsbreeze.databinding.ActivityFullNewsBinding
import com.rsstudio.newsbreeze.ui.base.BaseActivity
import com.rsstudio.newsbreeze.util.Constant
import com.rsstudio.newsbreeze.util.DataReceiveEvent
import com.rsstudio.newsbreeze.util.EventTagType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@AndroidEntryPoint
class FullNewsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFullNewsBinding

    @Inject
    lateinit var savedNewsDatabase: SavedNewsDatabase

    var item: ArticleData? = null

    var logTag = "@FullNewsActivity"

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
        binding.rlSave.setOnClickListener(this)
    }

    private fun initViews() {

        // update save icon

        if (SavedNewsHelper.allSavedNewsList.contains(item!!.id)) {
            binding.ivUnSaved.setImageResource(R.drawable.ic_save)
        } else {
            binding.ivUnSaved.setImageResource(R.drawable.ic_unsave)
        }

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
                binding.rlBb.background = resource
            }

        })

    }


    private fun onSaveNewsClicked(item: ArticleData) {

        val currentSavedNewsEntity = SavedNewsEntity (
            id = item.id!!,
            title = item.title,
            urlToImage = item.urlToImage,
            publishedAt = item.publishedAt,
        )

        if (item.author != null) {
            currentSavedNewsEntity.author = item.author
        }else{
            currentSavedNewsEntity.author = "Default Author"
        }

        val savedNewsDao = savedNewsDatabase.savedNewsDao()

        if (SavedNewsHelper.allSavedNewsList.contains(item.id)) {
            binding.ivUnSaved.setImageResource(R.drawable.ic_unsave)
            SavedNewsHelper.allSavedNewsList.remove(item.id!!)

            CoroutineScope(Dispatchers.IO).launch {
                savedNewsDao.deleteSavedNews(currentSavedNewsEntity)
                Log.d(logTag, "deleteNews ${item.id}")
            }
           // send it
            EventBus.getDefault().post(DataReceiveEvent(EventTagType.EVENT_ITEM_CHANGED,item))
        } else {
            binding.ivUnSaved.setImageResource(R.drawable.ic_save)
            SavedNewsHelper.allSavedNewsList.add(item.id!!)

            CoroutineScope(Dispatchers.IO).launch {
                savedNewsDao.insertSavedNews(currentSavedNewsEntity)
                Log.d(logTag, "savedNews ${item.id}")
            }

            EventBus.getDefault().post(DataReceiveEvent(EventTagType.EVENT_ITEM_CHANGED,item))
        }


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.ivBack -> {
                finish()
                this.overridePendingTransition(R.anim.enter, R.anim.exit)
            }

            R.id.rlSave -> {
                onSaveNewsClicked(item!!)
            }

        }
    }

    override fun onBackPressed() {
        finish()
        this.overridePendingTransition(R.anim.enter, R.anim.exit)
    }
}

