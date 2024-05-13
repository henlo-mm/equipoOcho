package com.appmovil.dogapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import  com.appmovil.dogapp.databinding.FragmentAddAppointmentBinding
import com.appmovil.dogapp.model.Appointment
import com.appmovil.dogapp.viewmodel.AppointmentViewModel

class AddAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentAddAppointmentBinding
    private val appointmentViewModel: AppointmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAppointmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores()
    }

    private fun controladores() {
        binding.GuardarCita.setOnClickListener {
            Log.d("SAVE", "Trying save now")
            saveAppointment()
        }
    }

    private fun saveAppointment(){
        val dogName = binding.nameEditText.text.toString()
        val breed = binding.razaEditText.text.toString()
        val nameOwner = binding.nameOwnerEditText.text.toString()
        val phone = binding.telephoneEditText.text.toString().toInt()
      //  val symptom = binding.sintomasSpinner.text.toString().toInt()
        val symptom = "Prueba"
        val appointment = Appointment(dogName = dogName, breed = breed, ownerName = nameOwner, phone = phone, symptom = symptom)
        appointmentViewModel.saveAppointment(appointment)
        Log.d("test",appointment.toString())
        //Toast.makeText(context,"Artículo guardado !!", Toast.LENGTH_SHORT).show()

    }
}