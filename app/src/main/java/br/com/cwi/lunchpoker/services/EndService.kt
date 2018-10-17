package br.com.cwi.lunchpoker.services

import br.com.cwi.lunchpoker.models.ApiReturn
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface EndService {
    @GET("v1/setlocais")
    fun setlocais(@Query("nome") nome: String,
                @Query("tipo") tipo: String): Observable<ApiReturn>
}