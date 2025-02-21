package com.example.myap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myap.databinding.ActivityBaseBinding

open class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // AppBar'ı destekleyici olarak ayarlayın
        setSupportActionBar(binding.toolbar)

        // İsterseniz AppBar başlığı dinamik olabilir
        supportActionBar?.title = "Notlarım Uygulaması"
    }

}
