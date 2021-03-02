package com.wahyupermadie.myapplication.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.wahyupermadie.myapplication.utils.ErrorType
import com.wahyupermadie.myapplication.utils.ErrorType.NetworkErrorException
import com.wahyupermadie.myapplication.utils.ErrorType.UnknownHostException
import com.wahyupermadie.myapplication.utils.extension.observeEvent
import retrofit2.HttpException

abstract class BaseActivity<T : ViewBinding, V: BaseViewModel> : AppCompatActivity() {

    lateinit var  binding : T

    abstract fun getViewModel() : V

    abstract fun getViewBinding() : T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        setupView(savedInstanceState)
        observeData(getViewModel())
        setupData()
        setupListener()
    }

    abstract fun setupView(savedInstanceState: Bundle?)

    private fun observeData(viewModel: V) {
        observeEvent(viewModel.error) {
            if (it is HttpException) {
                onError(NetworkErrorException(it.code(), it.message()))
            } else if (it is java.net.UnknownHostException) {
                onError(UnknownHostException(it.message ?: "No Connection"))
            }
        }

        observeEvent(viewModel.isLoading) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        observeEvent(viewModel.isNetworkAvailable, ::isNetworkAvailable)
    }

    abstract fun setupListener()
    abstract fun setupData()
    abstract fun isNetworkAvailable(isAvailable: Boolean)

    protected open fun showLoading() {}
    protected open fun hideLoading() {}
    protected open fun onError(errorType: ErrorType) {
        this.hideLoading()
    }
}