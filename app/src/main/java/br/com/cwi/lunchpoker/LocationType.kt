package br.com.cwi.lunchpoker

import com.google.gson.annotations.SerializedName

enum class LocationType (private val text: String) {

    @SerializedName("RESTAURANTE")
    RESTAURANTE("RESTAURANTE"),

    @SerializedName("HAMBURGUERIA")
    HAMBURGUERIA("HAMBURGUERIA"),

    @SerializedName("PIZZARIA")
    PIZZARIA("PIZZARIA"),

    @SerializedName("CHURRASCARIA")
    CHURRASCARIA("CHURRASCARIA"),

    @SerializedName("ORIENTAL")
    ORIENTAL("ORIENTAL"),

    @SerializedName("OUTRO")
    OUTRO("OUTRO");

    override fun toString(): String {
        return text
    }
}