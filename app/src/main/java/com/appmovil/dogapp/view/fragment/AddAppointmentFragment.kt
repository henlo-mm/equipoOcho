package com.appmovil.dogapp.view.fragment

import android.R
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appmovil.dogapp.databinding.FragmentAddAppointmentBinding
import com.appmovil.dogapp.model.Appointment
import com.appmovil.dogapp.model.DogBreedsResponse
import com.appmovil.dogapp.viewmodel.AppointmentViewModel
import com.appmovil.dogapp.webservice.DogApiService1
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        observerViewModel()
        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }

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
                            R.layout.simple_dropdown_item_1line,
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

    private fun controladores() {
        validateData()
    }

    private fun saveAppointment(){
        val dogName = binding.nameEditText.text.toString()
        val breed = binding.razaAutoCompleteTextView.text.toString()
        val nameOwner = binding.nameOwnerEditText.text.toString()
        val phone = binding.telephoneEditText.text.toString().toLong()
        val symptom = binding.sintomas.selectedItem.toString()

        val appointment = Appointment(dogName = dogName, breed = breed, ownerName = nameOwner, phone = phone, symptom = symptom)
        appointmentViewModel.saveAppointment(appointment)
        Toast.makeText(context,"¡Su cita se ha guardado correctamente!", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()

    }

    private fun validateData() {
        val listEditText = listOf(binding.nameEditText, binding.nameOwnerEditText, binding.razaAutoCompleteTextView, binding.telephoneEditText)

        fun isFormFilled(): Boolean {
            return listEditText.all { it.text.isNotEmpty() }
        }

        listEditText.forEach { editText ->
            editText.addTextChangedListener {
                binding.GuardarCita.isEnabled = isFormFilled()
                binding.GuardarCita.setTextColor(if (binding.GuardarCita.isEnabled) Color.WHITE else Color.parseColor("#FF000000"))
            }
        }

        binding.GuardarCita.isEnabled = isFormFilled()
        binding.GuardarCita.setTextColor(if (binding.GuardarCita.isEnabled) Color.WHITE else Color.parseColor("#FF000000"))

        binding.GuardarCita.setOnClickListener {
            val selectedSymptom = binding.sintomas.selectedItem.toString()
            if (selectedSymptom == "Seleccione un síntoma") {
                Snackbar.make(binding.root, "Por favor seleccione un síntoma.", Snackbar.LENGTH_LONG)
                    .show()

            } else {
                saveAppointment()
            }
        }
    }
    private fun observerViewModel(){
        observerListAppintment()
    }

    private fun observerListAppintment() {

        appointmentViewModel.getListAppointment()
        appointmentViewModel.listAppointment.observe(viewLifecycleOwner){ lista ->

        }
    }
}