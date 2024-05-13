package com.appmovil.dogapp.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.appmovil.dogapp.R


class MainActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_manager)

        val images = arrayOf(
            R.drawable.dog_app_logo,
            R.drawable.dog_app_logo,
            R.drawable.dog_app_logo
        )
        val breeds = arrayOf("Labrador", "Golden Retriever", "Bulldog")
        val symptoms = arrayOf("Fiebre", "Dolor de estómago", "Problemas respiratorios")
        val cardContainer = findViewById<LinearLayout>(R.id.cardContainer)


        // Iterar sobre los datos y crear las tarjetas dinámicamente
        for (i in images.indices) {
            val pet = Pet(images[i], breeds[i], symptoms[i])
            val cardView = createCardView(pet)
            cardContainer.addView(cardView)
        }
    }

    private fun createCardView(pet: Pet): CardView {
        val cardView = CardView(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        // Inflar el diseño de la tarjeta desde XML
        layoutInflater.inflate(R.layout.cardview, cardView, true)

        // Actualizar los elementos de la tarjeta con los datos de la mascota
        val petImageView = cardView.findViewById<ImageView>(R.id.petImageView)
        petImageView.setImageResource(pet.imageResId)

        val petNameTextView = cardView.findViewById<TextView>(R.id.petNameTextView)
        petNameTextView.text = pet.breed

        val symptomDescriptionTextView = cardView.findViewById<TextView>(R.id.symptomDescriptionTextView)
        symptomDescriptionTextView.text = pet.symptoms

        return cardView
    }
}

data class Pet(val imageResId: Int, val breed: String, val symptoms: String)
