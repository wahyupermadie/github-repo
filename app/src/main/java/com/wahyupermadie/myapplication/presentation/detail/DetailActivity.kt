package com.wahyupermadie.myapplication.presentation.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.wahyupermadie.myapplication.data.usecase.model.User
import com.wahyupermadie.myapplication.databinding.ActivityDetailBinding
import com.wahyupermadie.myapplication.presentation.base.BaseActivity
import com.wahyupermadie.myapplication.utils.extension.hideView
import com.wahyupermadie.myapplication.utils.extension.loadImage
import com.wahyupermadie.myapplication.utils.extension.observe
import com.wahyupermadie.myapplication.utils.extension.observeEvent
import com.wahyupermadie.myapplication.utils.extension.showToast
import com.wahyupermadie.myapplication.utils.extension.showView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    private val detailVm: DetailViewModel by viewModels()
    private lateinit var user: User
    private var isAlreadyFetched = false
    private var oldConnectedStatus = false

    override fun getViewModel(): DetailViewModel {
        return detailVm
    }

    override fun getViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun setupListener() {
        binding.btnSave.setOnClickListener {
            val note = binding.noteLayout.editText?.text.toString()
            lifecycleScope.launchWhenCreated {
                user.id?.let { it1 -> detailVm.updateUserNote(note, it1) }
            }
        }
    }

    override fun setupData() {
        observe(detailVm.user, ::setupDetail)
        observeEvent(detailVm.isUpdateSuccess) {
            showToast("Successfully update the user note")
        }
        user.let {
            lifecycleScope.launchWhenCreated {
                detailVm.fetchDetailUser(it.name!!)
            }
        }
    }

    private fun setupDetail(user: User) {
        binding.apply {
            user.avatarUrl?.let { ivAvatar.loadImage(it, this@DetailActivity) }
            tvFollowers.text = user.followers.toString()
            tvFollowing.text = user.following.toString()
            tvRepo.text = user.publicRepos.toString()
            tvName.text = user.name
            tvBlog.text = user.blog
            noteLayout.editText?.setText(user.note)
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

    override fun showLoading() {
        super.showLoading()
        binding.apply {
            shimmerDetail.showView()
            clDetailContainer.hideView()
        }
    }

    override fun hideLoading() {
        super.hideLoading()
        binding.apply {
            shimmerDetail.hideView()
            clDetailContainer.showView()
        }
    }

    override fun isNetworkAvailable(isAvailable: Boolean) {
        if (isAvailable) {
            if (!oldConnectedStatus) {
                oldConnectedStatus = true
                user.let {
                    lifecycleScope.launchWhenCreated {
                        detailVm.fetchDetailUser(it.name!!)
                    }
                }
            }
        } else {
            oldConnectedStatus = false
            if (isAlreadyFetched) {
                isAlreadyFetched = false
                user.let {
                    lifecycleScope.launchWhenCreated {
                        detailVm.fetchDetailUser(it.name!!)
                    }
                }
            }
            showToast("No Connection")
        }
    }
}