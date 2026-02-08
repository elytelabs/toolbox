package com.elytelabs.toolbox

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri

/**
 * Intent utilities for sharing, email, web, and app settings.
 */
object IntentKit {

    /**
     * Opens a URL in the default browser.
     */
    fun openWebPage(context: Context, url: String) {
        try {
            val webpage = url.toUri()
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            context.startActivity(intent)
        } catch (e: Exception) {
            UiKit.showToast(context, "No app can handle this action")
        }
    }

    /**
     * Shares text via the system share sheet.
     */
    fun shareText(context: Context, text: String, chooserTitle: String = "Share via") {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(intent, chooserTitle))
    }

    /**
     * Shares an image with optional text.
     */
    fun shareImage(context: Context, imageUri: Uri, text: String? = null, chooserTitle: String = "Share via") {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, imageUri)
            text?.let { putExtra(Intent.EXTRA_TEXT, it) }
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, chooserTitle))
    }

    /**
     * Shares multiple images with optional text.
     */
    fun shareImages(context: Context, imageUris: ArrayList<Uri>, text: String? = null, chooserTitle: String = "Share via") {
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "image/*"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
            text?.let { putExtra(Intent.EXTRA_TEXT, it) }
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, chooserTitle))
    }

    /**
     * Opens email app to send feedback with device info.
     */
    fun sendFeedbackEmail(
        context: Context,
        emails: Array<String>,
        appName: String,
        subject: String = "$appName Feedback"
    ) {
        val body = buildString {
            appendLine("---------------------------------------------------------------")
            appendLine("Please keep the following information")
            appendLine("---------------------------------------------------------------")
            appendLine("App Name: $appName")
            appendLine("App Version: ${getAppVersionName(context)} (${getAppVersionCode(context)})")
            appendLine("Device: ${Build.MANUFACTURER} ${Build.MODEL}")
            appendLine("Android Version: ${Build.VERSION.SDK_INT}")
            appendLine("---------------------------------------------------------------")
            appendLine()
        }
        sendEmail(context, emails, subject, body)
    }

    /**
     * Opens email app with pre-filled fields.
     */
    fun sendEmail(context: Context, emails: Array<String>, subject: String, body: String = "") {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:".toUri()
                putExtra(Intent.EXTRA_EMAIL, emails)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            UiKit.showToast(context, "No email app found")
        }
    }

    /**
     * Opens the app's settings page.
     */
    fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }

    /**
     * Opens the Play Store page for this app.
     */
    fun openPlayStore(context: Context, packageName: String = context.packageName) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri()))
        } catch (e: Exception) {
            openWebPage(context, "https://play.google.com/store/apps/details?id=$packageName")
        }
    }

    /**
     * Opens location settings.
     */
    fun openLocationSettings(context: Context) {
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    /**
     * Opens notification settings for this app (API 26+).
     */
    fun openNotificationSettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            }
            context.startActivity(intent)
        } else {
            openAppSettings(context)
        }
    }

    /**
     * Returns the app version code.
     */
    @Suppress("DEPRECATION")
    fun getAppVersionCode(context: Context): Long {
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.longVersionCode
        } else {
            info.versionCode.toLong()
        }
    }

    /**
     * Returns the app version name.
     */
    fun getAppVersionName(context: Context): String {
        return context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "Unknown"
    }
}
