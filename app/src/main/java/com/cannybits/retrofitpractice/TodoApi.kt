package com.cannybits.retrofitpractice

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


//defines all functions needed to access the API
interface TodoApi {

    @GET("/todos") //from the web after base url
    suspend fun getTodos(): Response<List<Todo>>
}