package com.makestorming.moneyexchange

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.makestorming.moneyexchange.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel:MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setSupportActionBar(toolbar)

        viewModel.toastMessage.observe(this, Observer { res ->
            if (res != null) {
                val message = res.format(this@MainActivity)
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

}

