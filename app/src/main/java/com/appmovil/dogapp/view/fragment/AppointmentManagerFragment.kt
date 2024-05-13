package com.appmovil.dogapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.appmovil.dogapp.R
import com.appmovil.dogapp.databinding.FragmentAppointmentManagerBinding


class AppointmentManagerFragment : Fragment() {
    private lateinit var binding: FragmentAppointmentManagerBinding
   // private val appointmenViewModel: AppointmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentManagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()

    }

    private fun controladores() {
        binding.floatingButton1.setOnClickListener {
            try {
                Log.d("NAVIGATION", "Trying to navigate now")
                findNavController().navigate(R.id.action_appointmentManagerFragment_to_addAppointmentFragment)
            } catch (e: Exception) {
                Log.e("NAVIGATION", "Navigation failed", e)
            }
        }
    }


}