package com.appmovil.dogapp.webservice
import com.appmovil.dogapp.model.DogBreedsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breed/{breed}/images")
    fun getRandomDogImage(@Path("breed") breed: String): Call<DogResponse>

}

interface DogApiService1 {
    @GET("breeds/list/all")
    fun getDogBreeds(): Call<DogBreedsResponse>
}

data class DogResponse(
    val status: String,
    val message: List<String>
)
