package com.astrear.composeplayground.presentation.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun toast(context: Context, @StringRes messageId: Int) {
    Toast.makeText(context, context.getString(messageId), Toast.LENGTH_SHORT).show()
}