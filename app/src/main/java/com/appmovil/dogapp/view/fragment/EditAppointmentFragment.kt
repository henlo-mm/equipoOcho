package com.appmovil.dogapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appmovil.dogapp.R
import com.appmovil.dogapp.databinding.FragmentEditAppointmentBinding
import com.appmovil.dogapp.model.Appointment
import com.appmovil.dogapp.model.DogBreedsResponse
import com.appmovil.dogapp.viewmodel.AppointmentViewModel
import com.appmovil.dogapp.webservice.DogApiService1
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentEditAppointmentBinding
    private val appointmenViewModel: AppointmentViewModel by viewModels()
    private lateinit var receivedAppointment: Appointment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAppointmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAppointmentData()
        controladores()
    }
    private fun controladores() {
        binding.btnEdit.setOnClickListener {
            updateAppointment()
        }
    }

    private fun getAppointmentData() {
        val receivedBundle = arguments

        receivedAppointment = receivedBundle?.getSerializable("appointmentData") as Appointment
        binding.nameEditText.setText(receivedAppointment.dogName)
        binding.razaAutoCompleteTextView.setText(receivedAppointment.breed)
        binding.nameOwnerEditText.setText(receivedAppointment.ownerName)
        binding.telephoneEditText.setText(receivedAppointment.phone.toString())

        val razaAutoCompleteTextView =  binding.razaAutoCompleteTextView

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(DogApiService1::class.java)

        val call = service.getDogBreeds()

        call.enqueue(object : Callback<DogBreedsResponse> {
            override fun onResponse(call: Call<DogBreedsResponse>, response: Response<DogBreedsResponse>) {
                if (response.isSuccessful) {
                    val breedsResponse = response.body()
                    breedsResponse?.let {
                        val breedsMap = it.message
                        val breedsList = mutableListOf<String>()
                        breedsMap.forEach { (breed, subBreeds) ->
                            if (subBreeds.isEmpty()) {
                                breedsList.add(breed)
                            } else {
                                subBreeds.forEach { subBreed ->
                                    breedsList.add("$subBreed $breed")
                                }
                            }
                        }
                        val adapter = ArrayAdapter<String>(
                            razaAutoCompleteTextView.context,
                            android.R.layout.simple_dropdown_item_1line,
                            breedsList
                        )
                        razaAutoCompleteTextView.setAdapter(adapter)
                    }
                }
            }

            override fun onFailure(call: Call<DogBreedsResponse>, t: Throwable) {
                Log.d("response", t.toString())
            }
        })

    }

    private fun updateAppointment(){

        val id = receivedAppointment.id
        val dogName = binding.nameEditText.text.toString()
        val breed = binding.razaAutoCompleteTextView.text.toString()
        val nameOwner = binding.nameOwnerEditText.text.toString()
        val phone = binding.telephoneEditText.text.toString().toInt()
        val symptom = receivedAppointment.symptom

        val appointment = Appointment(id = id, dogName = dogName, breed = breed, ownerName = nameOwner, phone = phone, symptom = symptom)
        appointmenViewModel.updateAppointment(appointment)

        findNavController().navigate(R.id.action_editAppointmentFragment_to_appointmentManagerFragment)

    }



}