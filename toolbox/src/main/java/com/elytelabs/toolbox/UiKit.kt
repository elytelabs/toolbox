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
import com.google.android.material.snackbar.Snackbar

/**
 * UI utilities for Toast, Snackbar, Clipboard, View visibility, Keyboard, and Validation.
 */
object UiKit {

    /**
     * Checks if the string is a valid email address.
     */
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Checks if the string is a valid phone number.
     */
    fun isPhoneValid(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    /**
     * Checks if the string is a valid URL.
     */
    fun isUrlValid(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }

    /**
     * Shows a short toast message.
     */
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Shows a long toast message.
     */
    fun showLongToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Copies text to clipboard. Shows toast on Android 12 and below.
     */
    fun copyToClipboard(context: Context, text: String, label: String = "text") {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            showToast(context, "Copied to clipboard")
        }
    }

    /**
     * Gets text from clipboard, or null if empty.
     */
    fun getClipboardText(context: Context): String? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return clipboard.primaryClip?.getItemAt(0)?.text?.toString()
    }

    /**
     * Shows or hides the view based on condition.
     */
    fun showOrHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    /**
     * Makes the view visible.
     */
    fun show(view: View) {
        view.visibility = View.VISIBLE
    }

    /**
     * Hides the view (GONE).
     */
    fun hide(view: View) {
        view.visibility = View.GONE
    }

    /**
     * Makes the view invisible (keeps layout space).
     */
    fun invisible(view: View) {
        view.visibility = View.INVISIBLE
    }

    /**
     * Hides the soft keyboard.
     */
    fun hideKeyboard(activity: Activity) {
        activity.currentFocus?.let { view ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Shows the soft keyboard for this view.
     */
    fun showKeyboard(view: View) {
        view.requestFocus()
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * Hides the soft keyboard from this view.
     */
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Shows a simple snackbar.
     */
    fun showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, message, duration).show()
    }

    /**
     * Shows a snackbar with an action button.
     */
    fun showSnackbar(
        view: View,
        message: String,
        actionText: String,
        duration: Int = Snackbar.LENGTH_LONG,
        action: () -> Unit
    ) {
        Snackbar.make(view, message, duration)
            .setAction(actionText) { action() }
            .show()
    }

    /**
     * Shows a snackbar from Activity's root view.
     */
    fun showSnackbar(activity: Activity, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        showSnackbar(activity.findViewById(android.R.id.content), message, duration)
    }

    /**
     * Shows a snackbar with action from Activity's root view.
     */
    fun showSnackbar(
        activity: Activity,
        message: String,
        actionText: String,
        duration: Int = Snackbar.LENGTH_LONG,
        action: () -> Unit
    ) {
        showSnackbar(activity.findViewById(android.R.id.content), message, actionText, duration, action)
    }

    /**
     * Shows an error-styled snackbar (red background).
     * @param backgroundColor Custom background color (default: vibrant red #D32F2F)
     * @param textColor Custom text color (default: white)
     */
    fun showErrorSnackbar(
        view: View,
        message: String,
        backgroundColor: Int = 0xFFD32F2F.toInt(),
        textColor: Int = 0xFFFFFFFF.toInt()
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
            setBackgroundTint(backgroundColor)
            setTextColor(textColor)
        }.show()
    }

    /**
     * Shows a success-styled snackbar (green background).
     * @param backgroundColor Custom background color (default: vibrant green #388E3C)
     * @param textColor Custom text color (default: white)
     */
    fun showSuccessSnackbar(
        view: View,
        message: String,
        backgroundColor: Int = 0xFF388E3C.toInt(),
        textColor: Int = 0xFFFFFFFF.toInt()
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(backgroundColor)
            setTextColor(textColor)
        }.show()
    }
}
