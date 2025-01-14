package com.elytelabs.toolbox

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.Toast

object UtilKit {

    fun shareText(context: Context, value: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, value)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share via")
        context.startActivity(shareIntent)
    }

    fun copyText(context: Context, value: String) {
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        // When setting the clipboard text.
        clipboardManager.setPrimaryClip(ClipData.newPlainText("text", value))
        // Only show a toast for Android 12 and lower.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
            context.showToast("Copied to Clipboard")
    }

    fun openWebPage(context: Context, url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            context.showToast("No app can handle this action")
        }
    }

    fun sendFeedbackEmail(context: Context, email: Array<String>, appName: String,) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, email)
            putExtra(Intent.EXTRA_SUBJECT, "$appName Feedback" )
            putExtra(Intent.EXTRA_TEXT,
                "--------------------------------------------------------------- \n " +
                        "Please keep the following information " +
                        "\n --------------------------------------------------------------- " +
                        "\n App Name: $appName " +
                        "\n App Version Name : ${getAppVersionName(context)} " +
                        "\n App Version Code : ${getAppVersionCode(context)} " +
                        "\n Device Manufacturer : ${Build.MANUFACTURER } " +
                        "\n Device Model : ${Build.MODEL}" +
                        "\n OS Version : ${Build.VERSION.SDK_INT}" +
                        "\n --------------------------------------------------------------- \n" + " ")
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            context.showToast("No email app found")
        }
    }

    @Suppress("Deprecation")
     fun getAppVersionCode(context: Context): Long? {
        val packageManager = context.packageManager
        val info = packageManager.getPackageInfo(context.packageName,0)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info?.longVersionCode
        } else {
            info?.versionCode?.toLong()
        }
    }

    fun getAppVersionName(context: Context): String? {
        val packageManager = context.packageManager
        val info = packageManager.getPackageInfo(context.packageName,0)

        return info?.versionName
    }

     fun Context.showToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun Context.showLongToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    fun View.showOrHide(isVisible: Boolean = false) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

}