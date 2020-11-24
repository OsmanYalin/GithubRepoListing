package com.osmanyalin.githubrepolisting.util

import android.view.View

fun View.disable() {
    this.alpha = 0.3F
    this.isClickable = false
}

fun View.enable() {
    this.alpha = 1F
    this.isClickable = true
}