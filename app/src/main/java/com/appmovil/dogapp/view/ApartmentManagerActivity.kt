package com.appmovil.dogapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appmovil.dogapp.databinding.ActivityAppointmentManagerBinding

class AppointmentManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppointmentManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentManagerBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }


}
