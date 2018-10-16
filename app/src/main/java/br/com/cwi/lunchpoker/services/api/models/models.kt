package br.com.cwi.lunchpoker.services.api.models

import java.io.Serializable

class RestaurantModel() {
    var name: String = ""
    var image: String = "http://www.capitalrealty.com.br/wp-content/uploads/2016/06/icone-restaurante-branco.png"
    var description: String = ""
}

class Restaurant(
        val id: Int,
        val nome: String,
        val tipo: String,
        val data: Int) : Serializable