package com.rsstudio.newsbreeze.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.databinding.ActivityMainBinding
import com.rsstudio.newsbreeze.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}