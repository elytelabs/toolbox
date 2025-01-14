package com.elytelabs.toolbox

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elytelabs.toolbox.UtilKit.copyText
import com.elytelabs.toolbox.UtilKit.getAppVersionCode
import com.elytelabs.toolbox.UtilKit.getAppVersionName
import com.elytelabs.toolbox.UtilKit.sendFeedbackEmail
import com.elytelabs.toolbox.UtilKit.shareText
import com.elytelabs.toolbox.UtilKit.showLongToast
import com.elytelabs.toolbox.UtilKit.showOrHide
import com.elytelabs.toolbox.UtilKit.showToast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val email = findViewById<Button>(R.id.email)
        email.setOnClickListener {
            val  emailId = "example@abc.com"
            if (emailId.isEmailValid()) {
                sendFeedbackEmail(this,
                    arrayOf(emailId), getString(R.string.app_name))
            } else {
                showToast("Invalid Email")
            }
        }

        val getAppVersionName = findViewById<Button>(R.id.getAppVersionName)
        getAppVersionName.setOnClickListener {
            showLongToast(getAppVersionName(this).toString())
        }

        val getAppVersionCode = findViewById<Button>(R.id.getAppVersionCode)
        getAppVersionCode.setOnClickListener {
            showToast(getAppVersionCode(this).toString())
        }

        val shareText = findViewById<Button>(R.id.shareText)
        shareText.setOnClickListener {
            shareText(this, " Text to share" )
        }

        val copyText = findViewById<Button>(R.id.copyText)
        copyText.setOnClickListener {
            copyText(this," Text to copy" )
        }

        val openWebPage = findViewById<Button>(R.id.openWebPage)
        openWebPage.setOnClickListener {
            UtilKit.openWebPage(this, "https://elytelabs.blogspot.com/")
        }

        val hideBtn = findViewById<Button>(R.id.hideBtn)
        hideBtn.setOnClickListener {
            hideBtn.showOrHide(false)
        }

    }
}