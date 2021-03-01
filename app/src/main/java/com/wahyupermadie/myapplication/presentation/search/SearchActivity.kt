package com.wahyupermadie.myapplication.presentation.search

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.data.usecase.search.SearchUserAdapter
import com.wahyupermadie.myapplication.databinding.ActivitySearchBinding
import com.wahyupermadie.myapplication.presentation.base.BaseActivity
import com.wahyupermadie.myapplication.presentation.detail.DetailActivity
import com.wahyupermadie.myapplication.utils.extension.hideView
import com.wahyupermadie.myapplication.utils.extension.observe
import com.wahyupermadie.myapplication.utils.extension.showView
import com.wahyupermadie.myapplication.utils.extension.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(), SearchUserAdapter.OnClickListener {

    private val searchVm: SearchViewModel by viewModels()
    private lateinit var searchUserAdapter: SearchUserAdapter

    override fun getViewModel(): SearchViewModel {
        return searchVm
    }

    override fun getViewBinding(): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun setupListener() {
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun setupData() {
        observe(searchVm.users) {
            if (it.isEmpty()) {
                binding.apply {
                    rvUser.hideView()
                    clEmptyState.showView()
                }
            } else {
                binding.apply {
                    rvUser.showView()
                    clEmptyState.hideView()
                }
                searchUserAdapter.addData(it)
            }
        }

        binding.etSearch.textChanges()
            .debounce(600)
            .onEach {
                searchVm.searchUser(it.toString())
            }
            .launchIn(lifecycleScope)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        searchUserAdapter = SearchUserAdapter()
        searchUserAdapter.apply {
            this.setListener(this@SearchActivity)
        }

        binding.rvUser.apply {
            adapter = searchUserAdapter
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }

        supportActionBar?.apply {
            title = "Search"
            this.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(user: User) {
        Intent(this, DetailActivity::class.java).apply {
            this.putExtra("data", user)
        }.run {
            startActivity(this)
        }
    }
}