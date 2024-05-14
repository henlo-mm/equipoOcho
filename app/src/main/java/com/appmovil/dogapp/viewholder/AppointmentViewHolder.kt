package com.appmovil.dogapp.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import com.appmovil.dogapp.databinding.FragmentAppointmentManagerBinding
import com.appmovil.dogapp.model.Appointment
import androidx.recyclerview.widget.RecyclerView
import com.appmovil.dogapp.databinding.CardviewBinding

class AppointmentViewHolder (binding: CardviewBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
        val bindingAppointment = binding
        val navController = navController

        fun setAppointment(appointment: Appointment) {
            bindingAppointment.petNameTextView.text = appointment.dogName
            bindingAppointment.appointmentTextView.text = "# ${appointment.id}"
            bindingAppointment.symptomDescriptionTextView.text = appointment.symptom
            /* bindingAppointment.petImageView. = appointment.breed */


           /* bindingAppointment.cvInventory.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("clave", appointment)
                navController.navigate(R.id.action_homeInventoryFragment_to_itemDetailsFragment, bundle
                )
            } */

        }
    }


