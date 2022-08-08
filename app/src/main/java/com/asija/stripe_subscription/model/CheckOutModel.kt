package com.asija.stripe_subscription.model

import com.google.gson.annotations.SerializedName

data class CheckOutModel(
    @SerializedName("amount")
    val amount: Int=1005,
    @SerializedName("country")
    val country: String="IN",
    @SerializedName("currency")
    val currency: String="INR",
    @SerializedName("complete_checkout_url")
    val completeUrl: String="https://asija.000webhostapp.com/test.html"
)