package br.com.cwi.lunchpoker.services

import br.com.cwi.lunchpoker.models.ApiReturn
import br.com.cwi.lunchpoker.models.ApiReturnLocations
import br.com.cwi.lunchpoker.models.ApiReturnRoom
import br.com.cwi.lunchpoker.models.Location
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface EndService {
    @GET("v1/setlocais")
    fun setLocais(@Query("nome") nome: String,
                  @Query("tipo") tipo: String): Observable<ApiReturn>

    @GET("v1/getsalas")
    fun getSalas(@Query("sala") sala: String): Observable<ApiReturnRoom>

    @GET("v1/setsalas")
    fun setSalas(@Query("locais") locais: String): Observable<ApiReturnRoom>

    @GET("v1/unsetvoto")
    fun unSetVoto(@Query("sala") sala: String,
                @Query("local") local: Int): Observable<ApiReturn>

    @GET("v1/setvoto")
    fun setVoto(@Query("sala") sala: String,
                @Query("local") local: Int): Observable<ApiReturn>

    @GET("v1/getvotos")
    fun getVoto(@Query("sala") sala: String): Observable<ApiReturn>

    @GET("v1/getlocais")
    fun getLocais(@Query("tipo") tipo: String = ""): Observable<ApiReturnLocations>
}