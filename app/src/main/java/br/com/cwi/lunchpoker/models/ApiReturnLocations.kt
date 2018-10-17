package br.com.cwi.lunchpoker.models

class ApiReturnLocations{
    var status:         Boolean = false
    var message:        String? = null
    var data:           MutableList<Location> = mutableListOf()
}