package com.appmovil.dogapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appmovil.dogapp.model.Appointment
import com.appmovil.dogapp.utils.Constants.NAME_DB

@Database(entities = [Appointment::class], version = 1)
abstract class AppointmentDB : RoomDatabase() {
    abstract fun appointmentDao() : AppointmentDao


    companion object{
        fun getDatabase(context: Context): AppointmentDB {
            return Room.databaseBuilder(
                context.applicationContext,
                AppointmentDB::class.java,
                NAME_DB
            ).build()
        }
    }
}