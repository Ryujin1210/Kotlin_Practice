package com.example.seoullibrary

import data.Library
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class SeoulOpenApi {
    companion object {
        val DOMAIN = "http://openapi.seoul.go.kr:8088"
        val API_KEY = "65774c6e7079776b3936734e4b4457"
    }
}

interface SeoulOpenService {
    @GET("{api_key}/json/SeoulPublicLibraryInfo/1/200/")
    fun getLibrary(@Path("api_key")key:String) : Call<Library>
}