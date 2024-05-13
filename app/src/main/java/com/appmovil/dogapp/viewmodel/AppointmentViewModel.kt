package com.appmovil.dogapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.appmovil.dogapp.model.Appointment
import com.appmovil.dogapp.repository.AppointmentRepository
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>()

    private val appointmentRepository = AppointmentRepository(context)

    fun saveAppointment(appointment: Appointment) {
        viewModelScope.launch {
            appointmentRepository.saveAppointment(appointment)
        }
    }
}