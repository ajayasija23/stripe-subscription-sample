package com.asija.stripe_subscription.rapydcheckout

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.asija.stripe_subscription.MyApp.Companion.publishableKey
import com.asija.stripe_subscription.databinding.ActivityPrebuiltUiBinding
import com.asija.stripe_subscription.utils.*
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import org.json.JSONObject

class RapydCheckout : AppCompatActivity() {
    private lateinit var mGooglePayConfiguration: PaymentSheet.GooglePayConfiguration
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var binding:ActivityPrebuiltUiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPrebuiltUiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etAmount.visibility= View.VISIBLE
        binding.payButton.setOnClickListener {
            showPaymentSheet()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.scheme=="completed"){
            "Bdhai Ho payment completed ho gyi".toast(this)
        }else{
            "So sad payment fail gyi".toast(this)
        }
    }

    fun showPaymentSheet(){
       ApiCaller(this).createCheckout(binding.etAmount.text.toString()) {
           it.log()
            runOnUiThread {
                try {
                    val redirectUrl=JSONObject(it).getJSONObject("data").getString("redirect_url")
                    openChromeTab(redirectUrl)
                    it.toast(this)
                }catch (e:Exception){

                }
            }
       }
    }
}