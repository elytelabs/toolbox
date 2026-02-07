package com.elytelabs.toolbox

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

// <-- Validation Extensions -->

/**
 * Checks if the string is a valid email address.
 */
fun String.isEmailValid(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Checks if the string is a valid phone number.
 */
fun String.isPhoneValid(): Boolean {
    return Patterns.PHONE.matcher(this).matches()
}

/**
 * Checks if the string is a valid URL.
 */
fun String.isUrlValid(): Boolean {
    return Patterns.WEB_URL.matcher(this).matches()
}

// <-- Toast Extensions -->

/**
 * Shows a short toast message.
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Shows a long toast message.
 */
fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

// <-- Clipboard Extensions -->

/**
 * Copies text to clipboard. Shows toast on Android 12 and below.
 */
fun Context.copyToClipboard(text: String, label: String = "text") {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
    // Android 13+ shows its own UI
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        showToast("Copied to clipboard")
    }
}

/**
 * Gets text from clipboard, or null if empty.
 */
fun Context.getClipboardText(): String? {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboard.primaryClip?.getItemAt(0)?.text?.toString()
}

// <-- View Visibility Extensions -->

/**
 * Shows or hides the view based on condition.
 */
fun View.showOrHide(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

/**
 * Makes the view visible.
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Hides the view (GONE).
 */
fun View.hide() {
    visibility = View.GONE
}

/**
 * Makes the view invisible (keeps layout space).
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

// <-- Keyboard Extensions -->

/**
 * Hides the soft keyboard.
 */
fun Activity.hideKeyboard() {
    currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

/**
 * Shows the soft keyboard for this view.
 */
fun View.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Hides the soft keyboard from this view.
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

// <-- Snackbar Extensions -->

/**
 * Shows a simple snackbar.
 */
fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

/**
 * Shows a snackbar with an action button.
 */
fun View.showSnackbar(
    message: String,
    actionText: String,
    duration: Int = Snackbar.LENGTH_LONG,
    action: () -> Unit
) {
    Snackbar.make(this, message, duration)
        .setAction(actionText) { action() }
        .show()
}

/**
 * Shows a snackbar from Activity's root view.
 */
fun Activity.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    findViewById<View>(android.R.id.content).showSnackbar(message, duration)
}

/**
 * Shows a snackbar with action from Activity's root view.
 */
fun Activity.showSnackbar(
    message: String,
    actionText: String,
    duration: Int = Snackbar.LENGTH_LONG,
    action: () -> Unit
) {
    findViewById<View>(android.R.id.content).showSnackbar(message, actionText, duration, action)
}

/**
 * Shows an error-styled snackbar (red background).
 */
fun View.showErrorSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(ContextCompat.getColor(context, android.R.color.holo_red_dark))
        setTextColor(ContextCompat.getColor(context, android.R.color.white))
    }.show()
}

/**
 * Shows a success-styled snackbar (green background).
 */
fun View.showSuccessSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).apply {
        setBackgroundTint(ContextCompat.getColor(context, android.R.color.holo_green_dark))
        setTextColor(ContextCompat.getColor(context, android.R.color.white))
    }.show()
}
