package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.adapter.UserAdapter
import com.example.myapplication.data.remote.ApiClient
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        GlobalScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main){

                flow {
                    val response = ApiClient
                        .apiService
                        .getSearch()

                    emit(response)
                }.onStart { // diajalakan ketika mulai
                    binding.progressBar.isVisible = true

                }.onCompletion { // diajalakan ketika selesai
                    binding.progressBar.isVisible = false

                }.catch { // diajalakan ketika ada error
                    Toast.makeText(this@MainActivity, it.message.toString(),
                        Toast.LENGTH_SHORT).show()
                    Log.e("Error", it.message.toString())

                }.collect{ // mendapatkan response
                    adapter.setData(it)

                }
            }
        }
    }
}