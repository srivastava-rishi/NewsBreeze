package com.rsstudio.newsbreeze.ui.savedNews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.ui.base.BaseActivity

class SavedNewsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_news)
    }
}