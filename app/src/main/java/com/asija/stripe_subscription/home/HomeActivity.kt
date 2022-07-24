package com.asija.stripe_subscription.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asija.stripe_subscription.card_only.CardOnlyPayment
import com.asija.stripe_subscription.custom_card.CustomCardActivity
import com.asija.stripe_subscription.databinding.HomeActivityBinding
import com.asija.stripe_subscription.prebuilt_ui.PreBuiltActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvCardOnly.setOnClickListener {
            startActivity(Intent(this,CardOnlyPayment::class.java))
        }
        binding.tvPreBuilt.setOnClickListener {
            startActivity(Intent(this,PreBuiltActivity::class.java))
        }
        binding.tvCustomCard.setOnClickListener {
            startActivity(Intent(this,CustomCardActivity::class.java))
        }
    }
}