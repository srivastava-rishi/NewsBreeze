package com.rsstudio.newsbreeze.ui.savedNews

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.databinding.ActivitySavedNewsBinding
import com.rsstudio.newsbreeze.ui.base.BaseActivity

class SavedNewsActivity : BaseActivity() ,View.OnClickListener {

    private lateinit var binding: ActivitySavedNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_saved_news)

        //
        initAction()
    }

    private fun initAction() {
        binding.ivBack.setOnClickListener(this)
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