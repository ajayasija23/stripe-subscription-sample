package com.asija.stripe_subscription.card_only

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.asija.stripe_subscription.databinding.ActivityCheckoutBinding
import com.asija.stripe_subscription.utils.ClientSecretProvider
import com.asija.stripe_subscription.utils.toast
import com.stripe.android.PaymentConfiguration
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.payments.paymentlauncher.PaymentResult
import kotlinx.coroutines.launch

/**
 * Checkout implementation for the app
 */
class CardOnlyPayment : AppCompatActivity() {

    private lateinit var paymentLauncher: PaymentLauncher
    private lateinit var binding: ActivityCheckoutBinding

    /**
     * Initialize the Google Pay API on creation of the activity
     *
     * @see AppCompatActivity.onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val paymentConfiguration = PaymentConfiguration.getInstance(applicationContext)
        paymentLauncher = PaymentLauncher.Companion.create(
            this,
            paymentConfiguration.publishableKey,
            paymentConfiguration.stripeAccountId,
            ::onPaymentResult
        )
        binding.payButton.setOnClickListener {
            ClientSecretProvider().getClientSecret{
                binding.cardInputWidget.paymentMethodCreateParams?.let { params ->
                    val confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, it)
                    lifecycleScope.launch {
                        paymentLauncher.confirm(confirmParams)
                    }
                }  //start payment flow
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

