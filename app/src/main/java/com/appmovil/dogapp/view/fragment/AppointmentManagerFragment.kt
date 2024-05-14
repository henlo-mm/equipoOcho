package com.appmovil.dogapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appmovil.dogapp.R
import com.appmovil.dogapp.databinding.FragmentAppointmentManagerBinding
import com.appmovil.dogapp.view.adapter.AppointmentAdapter
import com.appmovil.dogapp.viewmodel.AppointmentViewModel


class AppointmentManagerFragment : Fragment() {
    private lateinit var binding: FragmentAppointmentManagerBinding
    private val appointmenViewModel: AppointmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentManagerBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
        observadorViewModel()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Lleva la tarea al fondo, simulando que la app se minimiza
                activity?.moveTaskToBack(true)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
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

    private fun observadorViewModel(){
        observerListAppointment()
        observerProgress()
    }
    private fun observerListAppointment(){

        appointmenViewModel.getListAppointment()
        appointmenViewModel.listAppointment.observe(viewLifecycleOwner){ listAppointment ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = AppointmentAdapter(listAppointment, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()

        }

    }
    private fun observerProgress(){
        appointmenViewModel.progresState.observe(viewLifecycleOwner){status ->
            binding.progress.isVisible = status
        }
    }



}