package com.asija.stripe_subscription.paypal_client_side

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.asija.stripe_subscription.R
import com.asija.stripe_subscription.databinding.ActivityPaypalBinding
import com.asija.stripe_subscription.utils.log
import com.asija.stripe_subscription.utils.toast
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit

class PaypalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaypalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaypalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            rgPaymentType.setOnCheckedChangeListener { radioGroup, i ->
                if (i== R.id.rbClientSide){
                    binding.orderId.isVisible=false
                    setupButton()
                }else{
                    binding.orderId.isVisible=true
                    setupButton()
                }
            }
            setupButton()
        }
    }

    private fun setupButton() {
        binding.apply {
            paymentButtonContainer.setup(
                createOrder =
                CreateOrder { createOrderActions ->
                    if (rbClientSide.isChecked){
                        val order = Order(
                            intent = OrderIntent.CAPTURE,
                            appContext = AppContext(userAction = UserAction.PAY_NOW),
                            purchaseUnitList =
                            listOf(
                                PurchaseUnit(
                                    amount =
                                    Amount(currencyCode = CurrencyCode.USD, value = "10.00")
                                )
                            )
                        )
                        createOrderActions.create(order)
                    }else{
                        createOrderActions.set(orderId.text.toString())
                    }
                },
                onApprove = OnApprove { approval ->
                    approval.orderActions.capture { captureOrderResult ->
                        captureOrderResult.toString().toast(this@PaypalActivity)
                    }
                },
                onCancel = OnCancel {
                    "Buyer canceled the PayPal experience.".log()
                },
                onError = OnError { errorInfo ->
                    errorInfo.reason.log()
                }
            )
        }
    }
}