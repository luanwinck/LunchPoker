package br.com.cwi.lunchpoker.services

import com.google.firebase.firestore.FirebaseFirestore

class RestaurantesService {

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getRestaurantes() {
        val restaurantes =  firebaseFirestore.collection("restaurantes").get()

    }
}