package com.asija.stripe_subscription.custom_card

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.asija.stripe_subscription.databinding.ActivityCustomCardBinding
import com.asija.stripe_subscription.utils.ClientSecretProvider
import com.asija.stripe_subscription.utils.toast
import com.stripe.android.PaymentConfiguration
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.payments.paymentlauncher.PaymentResult
import kotlinx.coroutines.launch

class CustomCardActivity :AppCompatActivity() {
    private lateinit var binding:ActivityCustomCardBinding
    private lateinit var paymentLauncher: PaymentLauncher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCustomCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val paymentConfiguration = PaymentConfiguration.getInstance(applicationContext)
        paymentLauncher = PaymentLauncher.Companion.create(
            this,
            paymentConfiguration.publishableKey,
            paymentConfiguration.stripeAccountId,
            ::onPaymentResult
        )
        onButtonClick();
    }

    private fun onButtonClick() {
        binding.payButton.setOnClickListener {
            ClientSecretProvider().getClientSecret{
                val card =PaymentMethodCreateParams.Card(
                    number = binding.etCard.text.toString(),
                    expiryMonth = binding.etExpiry.validatedDate?.month,
                    expiryYear = binding.etExpiry.validatedDate?.year,
                    cvc = binding.etCvv.text.toString()
                )
                val paymentMethodCreateParams=PaymentMethodCreateParams.create(card)
                val confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(paymentMethodCreateParams, it)
                lifecycleScope.launch {
                    paymentLauncher.confirm(confirmParams)
                }
                /*binding.cardInputWidget.paymentMethodCreateParams?.let { params ->
                    val confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, it)
                    lifecycleScope.launch {
                        paymentLauncher.confirm(confirmParams)
                    }
                }*/  //start payment flow
            }

        }
    }

    private fun onPaymentResult(paymentResult: PaymentResult) {
        when (paymentResult) {
            is PaymentResult.Completed -> {
                "Completed!".toast(this)
            }
            is PaymentResult.Canceled -> {
                "Canceled!".toast(this)
            }
            is PaymentResult.Failed -> {
                // This string comes from the PaymentIntent's error message.
                ("Failed: " + paymentResult.throwable.message).toast(this)
            }
        }
    }
}