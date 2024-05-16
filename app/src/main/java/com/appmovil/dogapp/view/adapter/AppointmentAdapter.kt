package com.appmovil.dogapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appmovil.dogapp.databinding.CardviewBinding
import com.appmovil.dogapp.model.Appointment
import com.appmovil.dogapp.viewholder.AppointmentViewHolder
import androidx.navigation.NavController

class AppointmentAdapter(private val listAppointment:MutableList<Appointment>, private val navController: NavController):RecyclerView.Adapter<AppointmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val binding = CardviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AppointmentViewHolder(binding, navController )
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = listAppointment[position]
        holder.setAppointment(appointment)
    }

    override fun getItemCount(): Int {
        return listAppointment.size
    }
}
