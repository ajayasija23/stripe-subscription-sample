package com.asija.stripe_subscription

import android.app.Application
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction
import com.stripe.android.PaymentConfiguration

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            publishableKey
        )
        PayPalCheckout.setConfig(
            checkoutConfig = CheckoutConfig(
                application = this,
                clientId = PAYPAL_CLIENT_ID,
                environment = Environment.SANDBOX,
                currencyCode = CurrencyCode.USD,
                userAction = UserAction.PAY_NOW,
                settingsConfig = SettingsConfig(
                    loggingEnabled = true
                )
            )
        )
    }
    companion object{
        const val publishableKey="pk_test_51LOOhzSC2k8ef4XuTwbuptTZM3TEm0FtlZrM1sbTYzwFNifnriFRm0g92FV9ZYB8vvGu8w5MDV4SYy2sJguZj2XP00YkDYgngl"
        const val PAYPAL_CLIENT_ID="AZkipod43uWduJ6pUlW3kHmIUreVxTRO72JJKNIwkreoYOUj1fSnFsiFEhWN6jGtM1FVZRWmX84pZDmR"
    }
}