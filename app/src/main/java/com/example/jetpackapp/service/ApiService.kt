package com.example.jetpackapp.service

import com.example.jetpackapp.model.CustomData
import com.example.jetpackapp.model.CustomDataDTO
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("/comments?postId=1")
    suspend fun getData(): Response<List<CustomData>>

    @POST("/posts")
    suspend fun postData(@Body requestBody: CustomDataDTO): Response<CustomDataDTO>

    @DELETE("/posts/{id}")
    suspend fun deleteData(@Path("id") id: Int): Response<CustomData>

    @PUT("/posts/{id}")
    suspend fun putData(@Path("id") id: Int, @Body requestBody: CustomDataDTO): Response<CustomDataDTO>
}
