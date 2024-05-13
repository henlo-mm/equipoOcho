package com.appmovil.dogapp.repository

import android.content.Context
import com.appmovil.dogapp.data.AppointmentDB
import com.appmovil.dogapp.data.AppointmentDao
import com.appmovil.dogapp.model.Appointment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppointmentRepository (val context: Context) {
    private var appointmentDao:AppointmentDao = AppointmentDB.getDatabase(context).appointmentDao()

    suspend fun saveAppointment(appointment: Appointment) {
        withContext(Dispatchers.IO) {
            appointmentDao.saveAppointment(appointment)
        }
    }

}