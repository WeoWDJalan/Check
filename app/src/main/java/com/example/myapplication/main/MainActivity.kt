package com.example.myapplication.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.adapter.UserAdapter
import com.example.myapplication.data.model.SearchResponse
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.detail.DetailUser
import com.example.myapplication.utils.ResultUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter{
            Intent(this, DetailUser::class.java).apply {
                putExtra("username", it.login)
                startActivity(this)
            }
        }
    }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.getUser(p0.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean = false

        })

        viewModel.resultUser.observe(this){
            when(it){
                is ResultUtil.Success<*> ->{
                    adapter.setData(it.data as MutableList<SearchResponse.Item>)

                }
                is ResultUtil.Error ->{
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()

                }
                is ResultUtil.Loading ->{
                    binding.progressBar.isVisible = it.isLoading

                }
            }

        }

        viewModel.getUser()
    }
}