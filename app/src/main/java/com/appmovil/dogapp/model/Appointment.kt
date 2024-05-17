package com.appmovil.dogapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Appointment(
    @PrimaryKey (autoGenerate = true)
    val id:Int = 0,
    val dogName:String,
    val breed:String,
    val ownerName:String,
    val phone:Long,
    val symptom:String
): Serializable
