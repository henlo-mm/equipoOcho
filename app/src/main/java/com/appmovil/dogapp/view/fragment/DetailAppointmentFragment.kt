package com.appmovil.dogapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appmovil.dogapp.R
import com.appmovil.dogapp.databinding.FragmentDetailAppointmentBinding
import com.appmovil.dogapp.model.Appointment
import com.appmovil.dogapp.viewmodel.AppointmentViewModel
import com.appmovil.dogapp.webservice.DogApiService
import com.appmovil.dogapp.webservice.DogResponse
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentDetailAppointmentBinding
    private val appointmenViewModel: AppointmentViewModel by viewModels()
    private lateinit var receivedAppointment: Appointment

    private val dogApi: DogApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(DogApiService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailAppointmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAppointmentData()
        controladores()
    }

    private fun controladores() {
        binding.deleteButton.setOnClickListener {
            deleteAppointment()
        }

        binding.editButton.setOnClickListener {
            val bundle = Bundle()
            if (receivedAppointment != null) {
                bundle.putSerializable("appointmentData", receivedAppointment)
                Log.d("EditButton", "Navigating to edit with data: $receivedAppointment")
                findNavController().navigate(R.id.action_detailAppointmentFragment_to_editAppointmentFragment, bundle)
            } else {
                Log.e("EditButton", "Received appointment is null!")
            }
        }
    }

    private fun getAppointmentData() {
        val receivedBundle = arguments
        receivedAppointment = receivedBundle?.getSerializable("appointment") as Appointment
        binding.numberAppointment.text = "${receivedAppointment.id}"
        binding.titleTextDetailsName.text = "${receivedAppointment.dogName}"
        binding.petBreedName.text = "$ ${receivedAppointment.breed}"
        binding.petSymptoms.text = "${receivedAppointment.symptom}"
        binding.ownerName.text = "${receivedAppointment.ownerName}"
        binding.ownerPhone.text = "${receivedAppointment.phone}"

        Log.d("data", receivedAppointment.toString())
        loadRandomDogImage(receivedAppointment.breed)

    }

    private fun deleteAppointment(){
        appointmenViewModel.deleteAppointment(receivedAppointment)
        appointmenViewModel.getListAppointment()
        findNavController().popBackStack()
    }

    private fun loadRandomDogImage(breed: String) {
        dogApi.getRandomDogImage(breed).enqueue(object : Callback<DogResponse> {
            override fun onResponse(call: Call<DogResponse>, response: Response<DogResponse>) {
                if (response.isSuccessful) {
                    response.body()?.message?.let { imageUrl ->
                        Glide.with(requireContext())
                            .load(imageUrl[0])
                            .into(binding.petBreedImage)
                    }
                }
            }

            override fun onFailure(call: Call<DogResponse>, t: Throwable) {
                Log.e("API Failure", "Error loading image", t)
            }
        })
    }


}