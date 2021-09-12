package com.cannybits.retrofitpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cannybits.retrofitpractice.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException


const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()



        lifecycleScope.launchWhenCreated{
         binding.progressBar.isVisible = true
         val response = try {
             RetrofitInstance.api.getTodos()
         }   catch (e: IOException){
             Log.e(TAG,"IOException: you might not have internet connection")
             binding.progressBar.isVisible = false
             return@launchWhenCreated
         }
            catch (e: HttpException){
                Log.e(TAG,"HttpException: Unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body()!=null){
                todoAdapter.todos = response.body()!!
            } else {
                Log.e(TAG,"Response not successfully")
            }
            binding.progressBar.isVisible = false
        }
    }

    private fun setupRecyclerView() = binding.rvTodos.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}