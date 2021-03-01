package com.wahyupermadie.myapplication.presentation.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.api.load
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.databinding.ActivityDetailBinding
import com.wahyupermadie.myapplication.presentation.base.BaseActivity
import com.wahyupermadie.myapplication.utils.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    private val detailVm: DetailViewModel by viewModels()
    private lateinit var user: User

    override fun getViewModel(): DetailViewModel {
       return detailVm
    }

    override fun getViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun setupListener() {
        binding.btnSave.setOnClickListener {

        }
    }

    override fun setupData() {
        observe(detailVm.user, ::setupDetail)

        user.let {
            lifecycleScope.launchWhenCreated {
                detailVm.fetchDetailUser(it.name!!)
            }
        }
    }

    private fun setupDetail(user: User) {
        binding.apply {
            ivAvatar.load(user.avatarUrl)
            tvFollowers.text = user.followers.toString()
            tvFollowing.text = user.following.toString()
            tvRepo.text = user.publicRepos.toString()
            tvName.text = user.name
            tvBlog.text = user.blog
            etNote.setText(user.note ?: "")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        intent?.getParcelableExtra<User>("data")?.let {
            user = it
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = user.name?.capitalize(Locale.getDefault())
        }
    }
}