package com.rsstudio.newsbreeze.ui.savedNews

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.databinding.ActivitySavedNewsBinding
import com.rsstudio.newsbreeze.ui.base.BaseActivity

class SavedNewsActivity : BaseActivity() {

    private lateinit var binding: ActivitySavedNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_saved_news)
    }
}