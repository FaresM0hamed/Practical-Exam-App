package com.devfares.practicalexam.utils

import android.content.Context
import android.widget.Toast

// Extension function for showing toast message
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}