package com.elytelabs.toolbox

import android.os.Handler
import android.os.Looper

fun runOnUiThread(action: () -> Unit) {
    Handler(Looper.getMainLooper()).post { action() }
}

fun repeatTask(interval: Long, action: () -> Unit) {
    val handler = Handler(Looper.getMainLooper())
    val runnable = object : Runnable {
        override fun run() {
            action()
            handler.postDelayed(this, interval)
        }
    }
    handler.postDelayed(runnable, interval)
}

fun isMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}

fun delay(delay: Long, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(action, delay)
}