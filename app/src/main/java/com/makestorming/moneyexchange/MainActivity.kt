package com.makestorming.moneyexchange

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.makestorming.moneyexchange.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

// http://exchangeratesapi.io/

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel:MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setSupportActionBar(toolbar)

        MobileAds.initialize(this, getString(R.string.ad_app_id))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        viewModel.toastMessage.observe(this, Observer { res ->
            if (res != null) {
                val message = res.format(this@MainActivity)
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
        Glide.with(this).load(R.drawable.background).into(image)
    }
/*
    companion object {
        @JvmStatic
        @BindingAdapter("picture", requireAll = false)
        fun loadImage(view: ImageView, res: Drawable?) {
            Glide.with(view.context).load(res).into(view)
        }
        @JvmStatic
        @BindingAdapter("picture2", requireAll = false)
        fun loadImage2(view: ImageView, res: String?) {
            Glide.with(view.context).load(res).into(view)
        }

    }*/

}

