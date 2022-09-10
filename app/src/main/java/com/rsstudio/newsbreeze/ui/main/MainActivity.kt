package com.rsstudio.newsbreeze.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.rsstudio.newsbreeze.R
import com.rsstudio.newsbreeze.data.network.model.News
import com.rsstudio.newsbreeze.databinding.ActivityMainBinding
import com.rsstudio.newsbreeze.ui.base.BaseActivity
import com.rsstudio.newsbreeze.ui.main.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    var logTag = "@MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //
        initObservers()
    }

    private fun initObservers() {

        viewModel.newsData.observe(this) {

            if (it.isSuccessful) {
                val list: MutableList<News> = mutableListOf()
                list.add(it.body()!!)
                Log.d(logTag, "data: ${it.body()} ")

            }else {
                Log.d(logTag, "error: ${it.errorBody()} ")
            }
        }



    }
}