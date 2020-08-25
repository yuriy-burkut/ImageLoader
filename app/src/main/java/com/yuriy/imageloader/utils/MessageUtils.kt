package com.yuriy.imageloader.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class MessageUtils(private val context: Context) {

/*    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }*/

    fun showToast(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    fun showError(message: String? = null) {
        showToast(message ?: "Oops, an error occurred :(")
    }

    fun showError(throwable: Throwable) {
        showError(throwable.localizedMessage)
        throwable.printStackTrace()
    }
}