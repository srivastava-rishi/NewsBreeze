package com.rsstudio.newsbreeze.ui.savedNews


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        initView()
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

    private fun initView() {

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(logTag, "onTextChanged: $s")
                savedNewsAdapter.filter.filter(s)
            }
        })

    }

    private fun initObservers() {

        viewModel.localNewsData.observe(this) {

            if (it != null && it.isNotEmpty()) {
                val list: MutableList<SavedNewsEntity> = mutableListOf()
                list.addAll(it)
                Log.d(logTag, "initObservers: " + "check")
                Log.d(logTag, "initObservers: checkdata$it")
                savedNewsAdapter.submitList(list)
                binding.iLoader.visibility = View.GONE
                binding.cvContainer.visibility = View.VISIBLE


            } else {
                binding.tvError.text = "No saved news found"
                binding.tvError.visibility = View.VISIBLE
                //
                binding.rlTop.visibility = View.GONE
                binding.rlSearch.visibility = View.GONE
                binding.iLoader.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        finish()
        this.overridePendingTransition(R.anim.enter, R.anim.exit)
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