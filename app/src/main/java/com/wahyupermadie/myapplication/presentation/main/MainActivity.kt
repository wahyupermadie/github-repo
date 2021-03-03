package com.wahyupermadie.myapplication.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyupermadie.myapplication.databinding.ActivityMainBinding
import com.wahyupermadie.myapplication.presentation.base.BaseActivity
import com.wahyupermadie.myapplication.presentation.detail.DetailActivity
import com.wahyupermadie.myapplication.presentation.main.PagingLoadStateAdapter.LoadStateListener
import com.wahyupermadie.myapplication.presentation.search.SearchActivity
import com.wahyupermadie.myapplication.utils.extension.hideView
import com.wahyupermadie.myapplication.utils.extension.showToast
import com.wahyupermadie.myapplication.utils.extension.showView
import com.wahyupermadie.myapplication.utils.network.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.net.UnknownHostException

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), LoadStateListener {

    private val mainVm: MainViewModel by viewModels()
    private lateinit var mainAdapter: MainUserAdapter
    private var oldConnectedStatus = false

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

        binding.swipeHome.setOnRefreshListener {
            fetchUser()
        }
    }

    override fun setupData() {
        mainVm.users.observe(this, {
            if (it != null) {
                lifecycleScope.launchWhenCreated {
                    mainAdapter.submitData(it)
                }
            }
        })
    }

    override fun setupView(savedInstanceState: Bundle?) {
        mainAdapter = MainUserAdapter(this) {
            Intent(this, DetailActivity::class.java).apply {
                this.putExtra("data", it)
            }.run {
                startActivity(this)
            }
        }

        mainAdapter.addLoadStateListener {
            handleError(it)
        }

        val pagingStateAdapter = PagingLoadStateAdapter()
        pagingStateAdapter.setupListener(this)

        binding.rvUser.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
            adapter = mainAdapter.withLoadStateHeaderAndFooter(
                header = pagingStateAdapter,
                footer = pagingStateAdapter
            )
            this.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun handleError(it: CombinedLoadStates) {
        if (it.refresh is LoadState.Error) {
            hideLoading()
            binding.clErrorState.showView()
        }
    }

    override fun showLoading() {
        super.showLoading()
        binding.apply {
            rvUser.hideView()
            shimmerHome.showView()
            clErrorState.hideView()
        }
    }

    override fun hideLoading() {
        super.hideLoading()
        binding.apply {
            rvUser.showView()
            shimmerHome.hideView()
            swipeHome.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        fetchUser()
    }

    private fun fetchUser() {
        if (application.isNetworkAvailable()) {
            oldConnectedStatus = true
            mainVm.getUser()
        } else {
            oldConnectedStatus = false
            mainVm.getUser()
        }
    }

    override fun isNetworkAvailable(isAvailable: Boolean) {
        if (isAvailable) {
            if (!oldConnectedStatus) {
                oldConnectedStatus = true
                mainVm.getUser()
            }
        } else {
            oldConnectedStatus = false
            if (oldConnectedStatus) {
                mainVm.getUser()
            }
            showToast("No Connection")
        }
    }

    override fun onErrorLoad(error: Throwable) {
        when (error) {
            is HttpException, is UnknownHostException -> showToast("Please check your network connection")
            else -> showToast("Something went wrong, please try again latter")
        }
    }
}