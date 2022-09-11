package com.rsstudio.newsbreeze.ui.savedNews


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.data.local.entity.SavedNewsEntity
import com.rsstudio.newsbreeze.databinding.ActivitySavedNewsBinding
import com.rsstudio.newsbreeze.ui.base.BaseActivity
import com.rsstudio.newsbreeze.ui.savedNews.adapter.SavedNewsAdapter
import com.rsstudio.newsbreeze.ui.savedNews.viewModel.SavedNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class SavedNewsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySavedNewsBinding

    private lateinit var savedNewsAdapter: SavedNewsAdapter

    private val viewModel: SavedNewsViewModel by viewModels()

    var logTag = "@SavedNewsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_saved_news)

        //
        initAction()
        initRecyclerView()
        initObservers()
    }

    private fun initAction() {
        binding.ivBack.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        val llm = LinearLayoutManager(this)
        binding.rvSavedNews.setHasFixedSize(true)
        binding.rvSavedNews.layoutManager = llm
        savedNewsAdapter = SavedNewsAdapter(this)
        binding.rvSavedNews.adapter = savedNewsAdapter
    }

    private fun initObservers() {

        viewModel.localNewsData.observe(this) {

            if (it != null) {
                val list: MutableList<SavedNewsEntity> = mutableListOf()
                list.addAll(it)

                if(list != null){
                    Log.d(logTag, "initObservers: " + "check")
                    Log.d(logTag, "initObservers: checkdata$it")
                    savedNewsAdapter.submitList(list)
                    binding.iLoader.visibility = View.GONE
                    binding.cvContainer.visibility = View.VISIBLE
                }


            } else {
                Log.d(logTag, "initObservers: " + "error")
                binding.tvError.text = "No saved news found"
                binding.tvError.visibility = View.VISIBLE
                //
                binding.rlTop.visibility = View.GONE
                binding.rlSearch.visibility = View.GONE
                binding.iLoader.visibility = View.GONE
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.ivBack -> {
                finish()
                this.overridePendingTransition(R.anim.enter, R.anim.exit)
            }

        }
    }
}