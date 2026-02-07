package com.elytelabs.toolbox

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri

// <-- Web & Browser Intents -->

/**
 * Opens a URL in the default browser.
 */
fun Context.openWebPage(url: String) {
    try {
        val webpage = url.toUri()
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent)
    } catch (e: Exception) {
        showToast("No app can handle this action")
    }
}

// <-- Share Intents -->

/**
 * Shares text via the system share sheet.
 */
fun Context.shareText(text: String, chooserTitle: String = "Share via") {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(Intent.createChooser(intent, chooserTitle))
}

/**
 * Shares an image with optional text.
 */
fun Context.shareImage(imageUri: Uri, text: String? = null, chooserTitle: String = "Share via") {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/*"
        putExtra(Intent.EXTRA_STREAM, imageUri)
        text?.let { putExtra(Intent.EXTRA_TEXT, it) }
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(Intent.createChooser(intent, chooserTitle))
}

/**
 * Shares multiple images with optional text.
 */
fun Context.shareImages(imageUris: ArrayList<Uri>, text: String? = null, chooserTitle: String = "Share via") {
    val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
        type = "image/*"
        putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
        text?.let { putExtra(Intent.EXTRA_TEXT, it) }
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(Intent.createChooser(intent, chooserTitle))
}

// <-- Email Intents -->

/**
 * Opens email app to send feedback with device info.
 */
fun Context.sendFeedbackEmail(
    emails: Array<String>,
    appName: String,
    subject: String = "$appName Feedback"
) {
    val body = buildString {
        appendLine("---------------------------------------------------------------")
        appendLine("Please keep the following information")
        appendLine("---------------------------------------------------------------")
        appendLine("App Name: $appName")
        appendLine("App Version: ${getAppVersionName()} (${getAppVersionCode()})")
        appendLine("Device: ${Build.MANUFACTURER} ${Build.MODEL}")
        appendLine("Android Version: ${Build.VERSION.SDK_INT}")
        appendLine("---------------------------------------------------------------")
        appendLine()
    }
    sendEmail(emails, subject, body)
}

/**
 * Opens email app with pre-filled fields.
 */
fun Context.sendEmail(emails: Array<String>, subject: String, body: String = "") {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, emails)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        startActivity(intent)
    } catch (e: Exception) {
        showToast("No email app found")
    }
}

// <-- App & Settings Intents -->

/**
 * Opens the app's settings page.
 */
fun Context.openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}

/**
 * Opens the Play Store page for this app.
 */
fun Context.openPlayStore(packageName: String = this.packageName) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri()))
    } catch (e: Exception) {
        openWebPage("https://play.google.com/store/apps/details?id=$packageName")
    }
}

/**
 * Opens location settings.
 */
fun Context.openLocationSettings() {
    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
}

/**
 * Opens notification settings for this app (API 26+).
 */
fun Context.openNotificationSettings() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        }
        startActivity(intent)
    } else {
        openAppSettings()
    }
}

// <-- App Info Extensions -->

/**
 * Returns the app version code.
 */
@Suppress("DEPRECATION")
fun Context.getAppVersionCode(): Long {
    val info = packageManager.getPackageInfo(packageName, 0)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        info.longVersionCode
    } else {
        info.versionCode.toLong()
    }
}

/**
 * Returns the app version name.
 */
fun Context.getAppVersionName(): String {
    return packageManager.getPackageInfo(packageName, 0).versionName ?: "Unknown"
}
