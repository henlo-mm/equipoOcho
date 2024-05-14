package com.appmovil.dogapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appmovil.dogapp.model.Appointment
import com.appmovil.dogapp.repository.AppointmentRepository
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>()

    private val appointmentRepository = AppointmentRepository(context)

    private val _listAppointment = MutableLiveData<MutableList<Appointment>>()

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    val listAppointment: LiveData<MutableList<Appointment>> get() = _listAppointment

    fun saveAppointment(appointment: Appointment) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                appointmentRepository.saveAppointment(appointment)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun getListAppointment() {

        viewModelScope.launch {
            _progresState.value = true

            try {
                _listAppointment.value = appointmentRepository.getListAppointment()
                _progresState.value = false
            }  catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                appointmentRepository.deleteAppointment(appointment)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }

        }
    }

    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                appointmentRepository.updateAppointment(appointment)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }
}