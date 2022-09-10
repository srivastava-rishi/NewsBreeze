package com.rsstudio.newsbreeze.ui.fullnews

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.databinding.ActivityFullNewsBinding
import com.rsstudio.newsbreeze.ui.base.BaseActivity

class FullNewsActivity : BaseActivity() {

    private lateinit var binding: ActivityFullNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_news)
    }
}