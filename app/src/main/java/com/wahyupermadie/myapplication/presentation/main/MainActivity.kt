package com.wahyupermadie.myapplication.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyupermadie.myapplication.databinding.ActivityMainBinding
import com.wahyupermadie.myapplication.presentation.base.BaseActivity
import com.wahyupermadie.myapplication.presentation.detail.DetailActivity
import com.wahyupermadie.myapplication.presentation.search.SearchActivity
import com.wahyupermadie.myapplication.utils.extension.hideView
import com.wahyupermadie.myapplication.utils.extension.showToast
import com.wahyupermadie.myapplication.utils.extension.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val mainVm: MainViewModel by viewModels()
    private lateinit var mainAdapter: MainUserAdapter

    override fun getViewModel(): MainViewModel {
        return mainVm
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupListener() {
        binding.etSearch.setOnClickListener {
            Intent(this, SearchActivity::class.java).run {
                startActivity(this)
            }
        }
    }

    override fun setupData() {
        mainVm.checkConnection()
        mainVm.users.observe(this, {
            if (it != null) {
                mainAdapter.submitData(lifecycle, it)
            }
        })
    }

    override fun setupView(savedInstanceState: Bundle?) {
        mainAdapter = MainUserAdapter {
            Intent(this, DetailActivity::class.java).apply {
                this.putExtra("data", it)
            }.run {
                startActivity(this)
            }
        }

        binding.rvUser.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
            this.adapter = mainAdapter
            this.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun showLoading() {
        super.showLoading()
        binding.apply {
            rvUser.hideView()
            shimmerHome.showView()
        }
    }

    override fun hideLoading() {
        super.hideLoading()
        binding.apply {
            rvUser.showView()
            shimmerHome.hideView()
        }
    }

    override fun isNetworkAvailable(isAvailable: Boolean) {
        if (isAvailable) {
            mainVm.getUser()
        } else {
            showToast("No Connection")
        }
    }
}