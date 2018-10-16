package br.com.cwi.lunchpoker.models

import br.com.cwi.lunchpoker.LocationType

class ApiReturnRoom {
    var status:         Boolean = false
    var message:        String? = null
    var data:           Room? = null
}

class Room {
    var id:             Int = 0
    var data:           Long? = null
    var locais:         List<Location> = mutableListOf()
}

class Location {
    var id:             Int = 0
    var nome:           String? = null
    var tipo:           String? = null
    var data:           Long? = null
}