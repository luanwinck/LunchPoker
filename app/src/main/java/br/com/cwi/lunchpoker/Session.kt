package br.com.cwi.lunchpoker

import br.com.cwi.lunchpoker.models.Location

class Session {
    companion object {
        var roomId = ""
        var locales = mutableListOf<String>()
        var trueLocales = mutableListOf<Location>()
        var isHost = false

        fun getLocales(): String{
            var count = 0
            var retorno = ""
            for(local in locales){
                if(count > 0){
                   retorno += ","
                }
                retorno += local
                count++
            }
            return retorno
        }

        fun getLocal(id: String): String{
            var retorno = ""
            for(local in trueLocales){
                if(local.id.toString() == id){
                   retorno = local.nome!!
                }
            }
            return retorno
        }
    }
}