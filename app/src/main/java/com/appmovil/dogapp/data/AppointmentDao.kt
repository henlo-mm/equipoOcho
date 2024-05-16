package com.appmovil.dogapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.appmovil.dogapp.model.Appointment

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAppointment(appointment: Appointment)

    @Query("SELECT * FROM Appointment")
    suspend fun getListAppointment(): MutableList<Appointment>

    @Query("SELECT * FROM Appointment WHERE id = :id")
    suspend fun getListAppointmentById(id: Int): Appointment

    @Delete
    suspend fun deleteAppointment(appointment: Appointment)

    @Update
    suspend fun updateAppointment(appointment: Appointment)
}