package com.appmovil.dogapp.model

data class DogBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)