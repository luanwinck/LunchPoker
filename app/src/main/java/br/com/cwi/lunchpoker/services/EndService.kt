package br.com.cwi.lunchpoker.services

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface EndService {
    @GET("posts")
    fun getNews(@Query("categories") categories: String = "58"): Observable<List<String>>
}