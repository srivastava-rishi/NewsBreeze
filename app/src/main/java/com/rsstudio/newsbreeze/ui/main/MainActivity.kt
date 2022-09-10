package com.rsstudio.newsbreeze.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.data.network.model.ArticleData
import com.rsstudio.newsbreeze.data.network.model.News
import com.rsstudio.newsbreeze.databinding.ActivityMainBinding
import com.rsstudio.newsbreeze.ui.base.BaseActivity
import com.rsstudio.newsbreeze.ui.fullnews.FullNewsActivity
import com.rsstudio.newsbreeze.ui.main.adapter.MainAdapter
import com.rsstudio.newsbreeze.ui.main.viewModel.MainViewModel
import com.rsstudio.newsbreeze.util.Constant
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class MainActivity : BaseActivity() , MainAdapter.MainAdapterListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainAdapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()

    var logTag = "@MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //
        initRecyclerView()
        initObservers()
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
//                Log.d(logTag, "data: ${it.body()!!.articles[0].publishedAt} ")

            }else {
                Log.d(logTag, "error: ${it.errorBody()} ")
            }
        }
    }

    override fun onReadClicked(item: ArticleData) {
        val intent = Intent(this, FullNewsActivity::class.java)
        Log.d(logTag, "error89: $item")
        intent.putExtra(Constant.PARAM_NEWS, item as Serializable)
        startActivity(intent)
    }
}