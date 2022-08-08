package com.asija.stripe_subscription.utils

import android.util.Log
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ClientSecretProvider {
    private val url="https://asija.000webhostapp.com/server%20code/server_code.php"

     fun getClientSecret(onTokenReceive:(String)->Unit) {
        val client = OkHttpClient()
        val request= Request.Builder().url(url).post("".toRequestBody()).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ajay_asija",e.localizedMessage!!.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                //Log.d("ajay_asija",response.body?.string().toString())
                if (response.isSuccessful){
                    val jsonObject= JSONObject(response.body!!.string())
                    val clintToken=jsonObject.getString("client_secret")
                    onTokenReceive.invoke(clintToken)
                }
            }
        })
    }

}