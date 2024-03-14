package com.example.jetpackapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackapp.model.CustomData
import com.example.jetpackapp.model.CustomDataDTO
import com.example.jetpackapp.service.ApiService
import com.example.jetpackapp.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val textView: TextView = findViewById(R.id.responseTextView)

        viewModel.text.observe(this, Observer {
            textView.text = it
        })
    }


    fun getRequest(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getData() // Replace with respective calls
            withContext(Dispatchers.Main) {
                val textView: TextView = findViewById(R.id.responseTextView)
                val list: List<CustomData>? = response.body()
                if (list != null) {
                    textView.text = list[0].body
                }
            }
        }
    }

    fun postRequest(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    val title: String = findViewById<EditText>(R.id.title).text.toString()
                    val body: String = findViewById<EditText>(R.id.body).text.toString()
                    val userId: Int =
                        Integer.parseInt(findViewById<EditText>(R.id.userId).text.toString())

                    val customDataDTO = CustomDataDTO(title, body, userId, null)

                    val response =
                        apiService.postData(customDataDTO) // Replace with respective calls
                    val textView: TextView = findViewById(R.id.responseTextView)
                    val customData: CustomDataDTO? = response.body()
                    if (customData != null) {
                        textView.text = customData.toString()
                    }
                } catch (e: Exception) {
                    popup("Error", "Invalid inputs")
                    return@withContext;
                }
            }
        }
    }

    fun deleteRequest(view: View) {
        CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {
                try {
                    val id: Int = Integer.parseInt(findViewById<EditText>(R.id.id).text.toString())
                    val response = apiService.deleteData(id) // Replace with respective calls
                    val textView: TextView = findViewById(R.id.responseTextView)
                    textView.text = response.toString()
                } catch (e: Exception) {
                    popup("Error", "Invalid inputs")
                    return@withContext;
                }
            }
        }
    }

    fun putRequest(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    val id: Int = Integer.parseInt(findViewById<EditText>(R.id.id).text.toString())
                    val title: String = findViewById<EditText>(R.id.title).text.toString()
                    val body: String = findViewById<EditText>(R.id.body).text.toString()
                    val userId: Int =
                        Integer.parseInt(findViewById<EditText>(R.id.userId).text.toString())

                    val customeDataDTO = CustomDataDTO(title, body, userId, null)

                    val response =
                        apiService.putData(id, customeDataDTO) // Replace with respective calls
                    val textView: TextView = findViewById(R.id.responseTextView)
                    val customData: CustomDataDTO? = response.body()
                    if (customData != null) {
                        textView.text = customData.toString()
                    }
                } catch(e:Exception){
                    popup("Error", "Invalid inputs")
                    return@withContext;
                }
            }
        }
    }

    private fun popup(title: String, text: String) {
        val dialogFragment = CustomDialog(title, text)
        dialogFragment.show(supportFragmentManager, "customDialog")
    }

}