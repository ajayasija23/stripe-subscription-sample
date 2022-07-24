package com.asija.stripe_subscription.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast

fun String.toast(context: Context){
    Log.d(TAG, "toast: $this")
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}