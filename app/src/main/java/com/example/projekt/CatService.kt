package com.example.projekt




import okhttp3.ResponseBody
import retrofit2.http.GET

interface CatService {

    @GET("/fact")
    fun getFact(): retrofit2.Call<ResponseBody>

}