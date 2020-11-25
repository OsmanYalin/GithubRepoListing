package com.osmanyalin.githubrepolisting.util

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.disable() {
    this.alpha = 0.3F
    this.isClickable = false
}

fun View.enable() {
    this.alpha = 1F
    this.isClickable = true
}

fun ImageView.setTintColor(color: Int) {
    try {
        this.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
    } catch (e: Exception) {}
}