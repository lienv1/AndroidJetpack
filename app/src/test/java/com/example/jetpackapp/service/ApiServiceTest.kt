package com.example.jetpackapp.service

import com.example.jetpackapp.model.CustomDataDTO
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.mockwebserver.MockResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiServiceTest {

    private lateinit var service: ApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getDataTest() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("[{\"postId\":1, \"id\":1, \"name\": \"id labore ex et quam laborum\", \"email\": \"Eliseo@gardner.biz\", \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\"}]") // Adjust the JSON to match your data model
        mockWebServer.enqueue(mockResponse)

        val response = service.getData()
        val responseBody = response.body()

        assertEquals(true, response.isSuccessful)
        assertEquals(1, responseBody?.size)
        assertEquals(1, responseBody?.first()?.postId)
        assertEquals(1, responseBody?.first()?.id)
        assertEquals("Eliseo@gardner.biz", responseBody?.first()?.email)
        assertEquals("laudantium enim quasi est quidem magnam voluptate ipsam eos", responseBody?.first()?.body)
    }

    @Test
    fun postDataTest() =
        runBlocking {
            val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody("{\"title\": \"Lorem Ipsum\", \"body\": \"Lorem ipsum dolor sit amet, consectetur adipisici elit\",\"userId\":2,\"id\":101}")
            mockWebServer.enqueue(mockResponse)

            val customDataDTO = CustomDataDTO("Lorem Ipsum","Lorem ipsum dolor sit amet, consectetur adipisici elit",2, null)
            val response = service.postData(customDataDTO)
            val responseBody = response.body()

            assertEquals(true,response.isSuccessful)
            assertEquals("Lorem Ipsum", responseBody?.title)
            assertEquals("Lorem ipsum dolor sit amet, consectetur adipisici elit", responseBody?.body)
            assertEquals(2,responseBody?.userId)
            assertEquals(101,responseBody?.id)
    }

    @Test
    fun deleteDataTest() = runBlocking {
        val mockResponse = MockResponse().setResponseCode(200).setBody("{}")
        mockWebServer.enqueue(mockResponse)

        val response = service.deleteData(5)
        assertEquals(true,response.isSuccessful)
    }

    @Test
    fun putDataTest() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"title\": \"Lorem Ipsum\", \"body\": \"Lorem ipsum dolor sit amet, consectetur adipisici elit\",\"userId\":2,\"id\":101}")
        mockWebServer.enqueue(mockResponse)

        val customDataDTO = CustomDataDTO("Lorem Ipsum","Lorem ipsum dolor sit amet, consectetur adipisici elit",2,null)

        val response = service.putData(101, customDataDTO)
        val responseBody = response.body()

        assertEquals(true,response.isSuccessful)
        assertEquals("Lorem Ipsum", responseBody?.title)
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipisici elit", responseBody?.body)
        assertEquals(2,responseBody?.userId)
        assertEquals(101,responseBody?.id)

    }

}