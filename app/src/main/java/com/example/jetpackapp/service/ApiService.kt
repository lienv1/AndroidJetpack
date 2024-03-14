package com.example.jetpackapp.service

import com.example.jetpackapp.model.CustomeData
import com.example.jetpackapp.model.CustomeDataDTO
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("/comments?postId=1")
    suspend fun getData(): Response<List<CustomeData>>

    @POST("/posts")
    suspend fun postData(@Body requestBody: CustomeDataDTO): Response<CustomeDataDTO>

    @DELETE("/posts/{id}")
    suspend fun deleteData(@Path("id") id: Int): Response<CustomeData>

    @PUT("/posts/{id}")
    suspend fun putData(@Path("id") id: Int, @Body requestBody: CustomeDataDTO): Response<CustomeDataDTO>
}
