package com.asija.stripe_subscription.prebuilt_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asija.stripe_subscription.MyApp.Companion.publishableKey
import com.asija.stripe_subscription.databinding.ActivityPrebuiltUiBinding
import com.asija.stripe_subscription.utils.ClientSecretProvider
import com.asija.stripe_subscription.utils.toast
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

class PreBuiltActivity : AppCompatActivity() {
    private lateinit var mGooglePayConfiguration: PaymentSheet.GooglePayConfiguration
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var binding:ActivityPrebuiltUiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPrebuiltUiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mGooglePayConfiguration = PaymentSheet.GooglePayConfiguration(
            environment = PaymentSheet.GooglePayConfiguration.Environment.Test,
            countryCode = "IN",
            currencyCode = "INR" // Required for Setup Intents, optional for Payment Intents
        )
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        PaymentConfiguration.init(this, publishableKey)
        binding.payButton.setOnClickListener {
            showPaymentSheet()
        }

    }
    fun showPaymentSheet(){
        ClientSecretProvider().getClientSecret {
            paymentSheet.presentWithPaymentIntent(
                it,
                PaymentSheet.Configuration(
                    merchantDisplayName = "Ajay Asija",
                    allowsDelayedPaymentMethods = true,
                    googlePay = mGooglePayConfiguration
                )
            )
        }
    }
    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        // implemented in the next steps
        when (paymentSheetResult) {
            is PaymentSheetResult.Completed -> {
                "Completed!".toast(this)
            }
            is PaymentSheetResult.Canceled -> {
                "Canceled!".toast(this)
            }
            is PaymentSheetResult.Failed -> {
                // This string comes from the PaymentIntent's error message.
                ("Failed: " + paymentSheetResult.error.message).toast(this)
            }
        }
    }
}