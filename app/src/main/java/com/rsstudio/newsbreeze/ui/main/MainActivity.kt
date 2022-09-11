package com.rsstudio.newsbreeze.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.data.local.database.SavedNewsDatabase
import com.rsstudio.newsbreeze.data.local.entity.SavedNewsEntity
import com.rsstudio.newsbreeze.data.local.helper.SavedNewsHelper
import com.rsstudio.newsbreeze.data.network.model.ArticleData
import com.rsstudio.newsbreeze.data.network.model.News
import com.rsstudio.newsbreeze.databinding.ActivityMainBinding
import com.rsstudio.newsbreeze.ui.base.BaseActivity
import com.rsstudio.newsbreeze.ui.fullNews.FullNewsActivity
import com.rsstudio.newsbreeze.ui.main.adapter.MainAdapter
import com.rsstudio.newsbreeze.ui.main.viewModel.MainViewModel
import com.rsstudio.newsbreeze.ui.savedNews.SavedNewsActivity
import com.rsstudio.newsbreeze.util.Constant
import com.rsstudio.newsbreeze.util.DataReceiveEvent
import com.rsstudio.newsbreeze.util.EventTagType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.Serializable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() , MainAdapter.MainAdapterListener , View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainAdapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var savedNewsDatabase: SavedNewsDatabase

    var logTag = "@MainActivity"

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: DataReceiveEvent) {
        when {
            event.isTagMatchWith(EventTagType.EVENT_ITEM_CHANGED) -> {
                val sn = event.getNews()
                if(sn!=null){
                    mainAdapter.refreshItem(sn)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //
        initAction()
        clearDb()
        initRecyclerView()
        initObservers()
    }

    private fun initAction() {
        binding.rlSave.setOnClickListener(this)
    }


    private fun initRecyclerView() {
        val llm = LinearLayoutManager(this)
        binding.rvNews.setHasFixedSize(true)
        binding.rvNews.layoutManager = llm
        mainAdapter = MainAdapter(this,this)
        binding.rvNews.adapter = mainAdapter
    }

    private fun initObservers() {

        viewModel.newsData.observe(this) {

            if (it.isSuccessful) {
                val list: MutableList<News> = mutableListOf()
                list.add(it.body()!!)
                mainAdapter.submitList(list[0].articles)
                binding.iLoader.visibility = View.GONE

            }
            else {
                Log.d(logTag, "error: ${it.errorBody()} ")

                binding.tvError.text = "No news found"
                binding.tvError.visibility = View.VISIBLE
                //make it go away
                binding.rlTop.visibility = View.GONE
                binding.rlSearch.visibility = View.GONE
                binding.rvNews.visibility = View.GONE
                binding.iLoader.visibility = View.GONE

            }
        }
    }

    override fun onReadClicked(item: ArticleData) {
        val intent = Intent(this, FullNewsActivity::class.java)
        Log.d(logTag, "error89: $item")
        intent.putExtra(Constant.PARAM_NEWS, item as Serializable)
        startActivity(intent)
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun onSaveNewsClicked(item: ArticleData, imageBackground: RelativeLayout, image: ImageView, position: Int) {

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


        if (SavedNewsHelper.allSavedNewsList.contains(item.id)) {
            image.setImageResource(R.drawable.ic_unsave)
            imageBackground.backgroundTintList = this.resources.getColorStateList(R.color.grey9)
            SavedNewsHelper.allSavedNewsList.remove(item.id!!)

            val savedNewsDao = savedNewsDatabase.savedNewsDao()
            CoroutineScope(Dispatchers.IO).launch {
                savedNewsDao.deleteSavedNews(currentSavedNewsEntity)
                Log.d(logTag, "deleteNews ${item.id}")
            }
        } else {
            image.setImageResource(R.drawable.ic_save)
            imageBackground.backgroundTintList = this.resources.getColorStateList(R.color.green)
            SavedNewsHelper.allSavedNewsList.add(item.id!!)

            val savedNewsDao = savedNewsDatabase.savedNewsDao()
            CoroutineScope(Dispatchers.IO).launch {
                savedNewsDao.insertSavedNews(currentSavedNewsEntity)
                Log.d(logTag, "savedNews ${item.id}")
            }
        }


    }

    private fun clearDb() {

        SavedNewsHelper.allSavedNewsList.clear()

        val savedNewsDao = savedNewsDatabase.savedNewsDao()

        CoroutineScope(Dispatchers.IO).launch {
           savedNewsDao.deleteAllSavedNews()
            Log.d(logTag, "allSavedNewsDeletedSuccessfully")
        }

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.rlSave -> {
                val intent = Intent(this@MainActivity, SavedNewsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }

        }
    }

}