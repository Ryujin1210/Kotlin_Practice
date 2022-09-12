package com.example.nodejsconnecttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.nodejsconnecttest.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
   lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val url = ""

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(APIInterface::class.java)

        binding.btnGet.setOnClickListener {
            server.getRequest("name").enqueue(object: Callback<ResponseDC> {
                override fun onFailure(call: Call<ResponseDC>, t: Throwable) {

                }

                override fun onResponse(call: Call<ResponseDC>, response: Response<ResponseDC>) {
                    Log.d("response : ", response?.body().toString())
                }

            })
        }
        binding.btnPost.setOnClickListener {
            server.postRequest("id", "password").enqueue((object:Callback<ResponseDC>{
                override fun onFailure(call: Call<ResponseDC>, t: Throwable) {

                }
                override fun onResponse(call: Call<ResponseDC>, response: Response<ResponseDC>) {
                    Log.d("response : ", response?.body().toString())
                }
            }))
        }
        binding.btnUpdate.setOnClickListener {
            server.putRequest("board", "내용").enqueue((object:Callback<ResponseDC>{
                override fun onFailure(call: Call<ResponseDC>, t: Throwable) {

                }
                override fun onResponse(call: Call<ResponseDC>, response: Response<ResponseDC>) {
                    Log.d("response : ", response?.body().toString())
                }
            }))
        }
        binding.btnDelete.setOnClickListener {
            server.deleteRequest("board").enqueue((object:Callback<ResponseDC>{
                override fun onFailure(call: Call<ResponseDC>, t: Throwable) {

                }
                override fun onResponse(call: Call<ResponseDC>, response: Response<ResponseDC>) {
                    Log.d("response : ", response?.body().toString())
                }
            }))
        }
    }
}