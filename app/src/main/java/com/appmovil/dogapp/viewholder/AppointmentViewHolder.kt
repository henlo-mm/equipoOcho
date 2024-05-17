package com.appmovil.dogapp.viewholder

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.appmovil.dogapp.R
import com.appmovil.dogapp.databinding.CardviewBinding
import com.appmovil.dogapp.model.Appointment
import com.appmovil.dogapp.webservice.DogApiService
import com.appmovil.dogapp.webservice.DogResponse
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppointmentViewHolder (binding: CardviewBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
        val bindingAppointment = binding
        val navController = navController

        private val dogApi: DogApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(DogApiService::class.java)
        }

        fun setAppointment(appointment: Appointment) {
            Log.d("ddd", appointment.toString())

            bindingAppointment.petNameTextView.text = appointment.dogName
            bindingAppointment.appointmentTextView.text = "# ${appointment.id}"
            bindingAppointment.symptomDescriptionTextView?.text = appointment.symptom
            loadRandomDogImage(appointment.breed)
            bindingAppointment.itemCardView.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("appointment", appointment)
                navController.navigate(
                    R.id.action_appointmentManagerFragment_to_detailAppointmentFragment, bundle
                )
            }

        }

        private fun loadRandomDogImage(breed: String) {
            dogApi.getRandomDogImage(breed).enqueue(object : Callback<DogResponse> {
                override fun onResponse(call: Call<DogResponse>, response: Response<DogResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.message?.let { imageUrl ->
                            Glide.with(itemView.context)
                                .load(imageUrl[0])
                                .into(bindingAppointment.petImageView)
                        }
                    }
                }

                override fun onFailure(call: Call<DogResponse>, t: Throwable) {
                    Log.e("API Failure", "Error loading image", t)
                }
            })
        }

    }


