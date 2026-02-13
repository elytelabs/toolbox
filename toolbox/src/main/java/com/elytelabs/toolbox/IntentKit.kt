@file:Suppress("unused")

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
     * Safely starts an intent.
     */
    private inline fun safeStartIntent(context: Context, intentBuilder: () -> Intent) {
        try {
            val intent = intentBuilder()
            context.startActivity(intent)
        } catch (e: Exception) {
            UiKit.showToast(context, "No app found to handle this action")
        }
    }

    /**
     * Opens a URL in the default browser.
     */
    fun openWebPage(context: Context, url: String) {
        safeStartIntent(context) {
            Intent(Intent.ACTION_VIEW, url.toUri())
        }
    }

    /**
     * Opens a URI with a fallback URL if the primary URI fails.
     * Useful for deep links (e.g. market://) with web fallbacks.
     */
    fun openUriWithFallback(context: Context, primaryUri: String, fallbackUrl: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, primaryUri.toUri())
            context.startActivity(intent)
        } catch (e: Exception) {
            openWebPage(context, fallbackUrl)
        }
    }

    /**
     * Shares text via the system share sheet.
     */
    fun shareText(context: Context, text: String, chooserTitle: String = "Share via") {
        safeStartIntent(context) {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }.let { Intent.createChooser(it, chooserTitle) }
        }
    }

    /**
     * Shares an image with optional text.
     */
    fun shareImage(context: Context, imageUri: Uri, text: String? = null, chooserTitle: String = "Share via") {
        safeStartIntent(context) {
            Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, imageUri)
                text?.let { putExtra(Intent.EXTRA_TEXT, it) }
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }.let { Intent.createChooser(it, chooserTitle) }
        }
    }

    /**
     * Shares multiple images with optional text.
     */
    fun shareImages(context: Context, imageUris: ArrayList<Uri>, text: String? = null, chooserTitle: String = "Share via") {
        safeStartIntent(context) {
            Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                type = "image/*"
                putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
                text?.let { putExtra(Intent.EXTRA_TEXT, it) }
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }.let { Intent.createChooser(it, chooserTitle) }
        }
    }

    /**
     * Opens email app to send feedback with device info.
     */
    fun sendFeedbackEmail(
        context: Context,
        emails: Array<String>,
        appName: String,
        subject: String = "Feedback for $appName v ${getAppVersionName(context)}"
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
        safeStartIntent(context) {
            Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:".toUri()
                putExtra(Intent.EXTRA_EMAIL, emails)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }
        }
    }

    /**
     * Opens the app's settings page.
     */
    fun openAppSettings(context: Context) {
        safeStartIntent(context) {
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
        }
    }

    /**
     * Opens the Play Store page for this app.
     */
    fun openPlayStore(context: Context, packageName: String = context.packageName) {
        openUriWithFallback(
            context,
            "market://details?id=$packageName",
            "https://play.google.com/store/apps/details?id=$packageName"
        )
    }

    /**
     * Opens location settings.
     */
    fun openLocationSettings(context: Context) {
        safeStartIntent(context) {
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        }
    }

    /**
     * Opens notification settings for this app (API 26+).
     */
    fun openNotificationSettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            safeStartIntent(context) {
                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
            }
        } else {
            openAppSettings(context)
        }
    }

    /**
     * Returns the app version code.
     */
    @Suppress("DEPRECATION")
    fun getAppVersionCode(context: Context): Long {
        return try {
            val info = context.packageManager.getPackageInfo(context.packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                info.longVersionCode
            } else {
                info.versionCode.toLong()
            }
        } catch (e: Exception) {
            -1L
        }
    }

    /**
     * Returns the app version name.
     */
    fun getAppVersionName(context: Context): String {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "Unknown"
        } catch (e: Exception) {
            "Unknown"
        }
    }
}
