package com.appmovil.dogapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appmovil.dogapp.databinding.LoginBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: LoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.login)

        binding.animationView.setOnClickListener {
            startActivity(Intent(this, BiometricActivity::class.java))
        }

        //setContentView(R.layout.activity_nueva_cita)

    }
}