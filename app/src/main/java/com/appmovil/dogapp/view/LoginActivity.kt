package com.appmovil.dogapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appmovil.dogapp.R
import com.appmovil.dogapp.databinding.LoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login)

        binding.animationView.setOnClickListener {
            startActivity(Intent(this, BiometricActivity::class.java))
        }
    }
}