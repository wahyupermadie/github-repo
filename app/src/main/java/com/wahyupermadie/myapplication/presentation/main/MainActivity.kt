package com.wahyupermadie.myapplication.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyupermadie.myapplication.R
import com.wahyupermadie.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapters = MainUserAdapter {

        }

        binding.rvUser.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
            this.adapter = adapters
            this.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.users.observe(this, {
            if (it != null) {
                adapters.submitData(lifecycle, it)
            }
        })

        viewModel.getUser()
    }
}