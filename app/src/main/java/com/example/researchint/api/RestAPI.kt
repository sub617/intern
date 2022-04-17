package com.example.internship2.api

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface RestAPI {

    @POST("/gps/create")
    fun createUser(@Body myCoordinatesModel: MyCoordinatesModel?): Call<ResponseBody>

    companion object {
        const val baseurl = "http://192.168.163.103:8000"
    }
}