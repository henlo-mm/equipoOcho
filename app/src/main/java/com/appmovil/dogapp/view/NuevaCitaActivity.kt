package com.appmovil.dogapp.view;

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.appmovil.dogapp.model.DogBreedsResponse
import com.appmovil.dogapp.webservice.DogApiService1
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.appmovil.dogapp.R

public class NuevaCitaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_cita)

        val razaAutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.razaAutoCompleteTextView)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)
        razaAutoCompleteTextView.setAdapter(adapter)

        // Inicializar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear instancia de la interfaz de servicio
        val service = retrofit.create(DogApiService1::class.java)

        // Realizar la solicitud HTTP
        val call = service.getDogBreeds()
        call.enqueue(object : Callback<DogBreedsResponse> {
            override fun onResponse(call: Call<DogBreedsResponse>, response: Response<DogBreedsResponse>) {
                if (response.isSuccessful) {
                    val breedsResponse = response.body()
                    breedsResponse?.let {
                        val breedsMap = it.message
                        val breedsList = mutableListOf<String>()
                        breedsMap.forEach { (breed, subBreeds) ->
                            if (subBreeds.isEmpty()) {
                                breedsList.add(breed)
                            } else {
                                subBreeds.forEach { subBreed ->
                                    breedsList.add("$subBreed $breed")
                                }
                            }
                        }
                        adapter.addAll(breedsList)
                    }
                }
            }

            override fun onFailure(call: Call<DogBreedsResponse>, t: Throwable) {
                // Manejar errores de solicitud
            }
        })
    }
}

