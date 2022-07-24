package com.asija.stripe_subscription

import android.app.Application
import com.stripe.android.PaymentConfiguration

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            publishableKey
        )
    }
    companion object{
        const val publishableKey="pk_test_51LOOhzSC2k8ef4XuTwbuptTZM3TEm0FtlZrM1sbTYzwFNifnriFRm0g92FV9ZYB8vvGu8w5MDV4SYy2sJguZj2XP00YkDYgngl"
    }
}