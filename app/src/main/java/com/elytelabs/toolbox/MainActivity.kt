package com.elytelabs.toolbox

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elytelabs.toolbox.UtilKit.getAppVersionCode
import com.elytelabs.toolbox.UtilKit.getAppVersionName
import com.elytelabs.toolbox.UtilKit.sendFeedbackEmail
import com.elytelabs.toolbox.UtilKit.showLongToast
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
            sendFeedbackEmail(this,
                arrayOf("example@abc.com"), getString(R.string.app_name))
        }

        val getAppVersionName = findViewById<Button>(R.id.getAppVersionName)
        getAppVersionName.setOnClickListener {
            showLongToast(getAppVersionName(this).toString())
        }

        val getAppVersionCode = findViewById<Button>(R.id.getAppVersionCode)
        getAppVersionCode.setOnClickListener {
            showToast(getAppVersionCode(this).toString())
        }

    }
}