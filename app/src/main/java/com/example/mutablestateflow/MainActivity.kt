package com.example.mutablestateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.example.mutablestateflow.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    lateinit var myViewModel: MyViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]

        binding.clickMe.setOnClickListener {
            myViewModel.login(binding.username.text.toString(), binding.password.text.toString())
        }
        lifecycle.coroutineScope.launchWhenCreated {
            myViewModel.state.collect {
                when (it) {
                    is MyViewModel.StateController.Empty ->{
                       // binding.progress.visibility = View.VISIBLE

                    }
                    is MyViewModel.StateController.Error ->{
                        binding.progress.visibility = View.GONE
                        Snackbar.make(binding.root,it.message,Snackbar.LENGTH_SHORT).show()
                    }
                    is MyViewModel.StateController.Loading ->{
                        binding.progress.visibility = View.VISIBLE
                    }
                    is MyViewModel.StateController.Success -> {
                        binding.progress.visibility = View.GONE
                        Snackbar.make(binding.root,"Success",Snackbar.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }
}