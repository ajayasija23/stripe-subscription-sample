package com.asija.stripe_subscription.utils

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Base64.*
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.asija.stripe_subscription.R
import com.asija.stripe_subscription.model.CheckOutModel
import com.google.gson.Gson
import okhttp3.Headers
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


fun String.toast(context: Context){
    log()
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}
fun String.log() {
    Log.d(TAG, "api_log $this")
}

fun getBody(amount: String):String{
    return Gson().toJson(CheckOutModel(amount = amount.toInt()))
}


fun createHeader(endPoint: String, httpMethod: String, bodyString: String): Headers {
    val accessKey = "066809634F432CC90663"
    val secretKey =
        "8697df6e38928c90be21d9ffefa85b8b0752933cf9097d8e7e1e8969299d825671f8a287c9fbbc8d"
    val timestamp = System.currentTimeMillis() / 1000L
    timestamp.toString().log()
    val salt = getSalt()
    // Must be a String or an empty String.
    val toEnc: String =
        httpMethod + endPoint + salt + timestamp+ accessKey + secretKey + bodyString
    toEnc.log()
    val hashCode = hmacDigest(toEnc, secretKey, "HmacSHA256")
    val signature = base64Encode(hashCode.toString())
    return Headers.Builder()
        .add("Content-Type", "application/json")
        .add("access_key", accessKey)
        .add("salt", salt)
        .add("timestamp", timestamp.toString())
        .add("signature", signature)
        .build()
}

fun base64Encode(StrhashCode: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Base64.getEncoder().encodeToString(StrhashCode.toByteArray())
    } else {
       ""
    }
}


fun getSalt(): String {
    val leftLimit = 97 // letter 'a'
    val rightLimit = 122 // letter 'z'
    val targetStringLength = 10
    val random = Random()
    val buffer = StringBuilder(targetStringLength)
    for (i in 0 until targetStringLength) {
        val randomLimitedInt =
            leftLimit + (random.nextFloat() * (rightLimit - leftLimit + 1)).toInt()
        buffer.append(randomLimitedInt.toChar())
    }
    return buffer.toString()
}

fun hmacDigest(msg: String, keyString: String, algo: String?): String? {
    var digest: String? = null
    try {
        val key = SecretKeySpec(keyString.toByteArray(charset("ASCII")), algo)
        val mac = Mac.getInstance(algo)
        mac.init(key)
        val bytes = mac.doFinal(msg.toByteArray(charset("UTF-8")))
        val hash = StringBuffer()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                hash.append('0')
            }
            hash.append(hex)
        }
        digest = hash.toString()
    } catch (e: UnsupportedEncodingException) {
        println("hmacDigest UnsupportedEncodingException")
    } catch (e: InvalidKeyException) {
        println("hmacDigest InvalidKeyException")
    } catch (e: NoSuchAlgorithmException) {
        println("hmacDigest NoSuchAlgorithmException")
    }
    return digest
}

fun Activity.openChromeTab(url:String){

    val defaultColors = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(ContextCompat.getColor(this, R.color.teal_700))
        .build()
    val chromeIntent= CustomTabsIntent.Builder().setDefaultColorSchemeParams(defaultColors).build()
    chromeIntent.launchUrl(this, Uri.parse(url))
}