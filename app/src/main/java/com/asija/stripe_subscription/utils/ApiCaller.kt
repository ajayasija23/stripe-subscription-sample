package com.asija.stripe_subscription.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ApiCaller(val mContext: Context) {
    val url ="https://sandboxapi.rapyd.net/v1/checkout"
    fun createCheckout(amount: String,onTokenReceive:(String)->Unit) {
        val client = OkHttpClient()
        val request= Request.Builder()
            .url(url).headers(createHeader("/v1/checkout","post", getBody(amount))).post(getBody(
                amount
            ).toRequestBody()).build()
        request.toString().log()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ajay_asija",e.localizedMessage!!.toString())
            }

            override fun onResponse(call: Call, response: Response) {
               onTokenReceive.invoke(response.body!!.string())
            }
        })
    }

}