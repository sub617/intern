package com.example.internship2.api

import retrofit2.Retrofit
import com.example.internship2.api.RestAPI
import com.example.internship2.api.ApiUtilities
import retrofit2.converter.gson.GsonConverterFactory


object ApiUtilities {
    private var retrofit: Retrofit? = null
    val apiInterface: RestAPI
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(RestAPI.baseurl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(RestAPI::class.java)
        }
}