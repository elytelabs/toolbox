package com.elytelabs.toolbox

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        setupColorGenerator()
        setupUiKitToastSnackbar()
        setupUiKitClipboardView()
        setupUiKitValidation()
        setupIntentKitShareWeb()
        setupIntentKitSettings()
        setupDialogKitAlerts()
        setupDialogKitLists()
    }

    //  ColorGenerator Examples

    private fun setupColorGenerator() {
        val demoView = findViewById<View>(R.id.demoView)

        findViewById<Button>(R.id.btnRandomColor).setOnClickListener {
            val color = ColorGenerator.getRandomColor()
            demoView.setBackgroundColor(color)
            UiKit.showToast(this, "Random color applied!")
        }

        findViewById<Button>(R.id.btnAvatarColor).setOnClickListener {
            DialogKit.showInputDialog(
                context = this,
                title = "Avatar Color",
                hint = "Enter name or email",
                onSubmit = { input ->
                    val color = ColorGenerator.getColorForString(input)
                    demoView.setBackgroundColor(color)
                    UiKit.showToast(this, "Avatar color for: $input")
                }
            )
        }
    }

    // UiKit - Toast & Snackbar

    private fun setupUiKitToastSnackbar() {
        findViewById<Button>(R.id.btnShortToast).setOnClickListener {
            UiKit.showToast(this, "This is a short toast")
        }

        findViewById<Button>(R.id.btnLongToast).setOnClickListener {
            UiKit.showLongToast(this, "This is a longer toast message")
        }

        findViewById<Button>(R.id.btnSnackbar).setOnClickListener {
            UiKit.showSnackbar(this, "This is a snackbar message")
        }

        findViewById<Button>(R.id.btnSnackbarAction).setOnClickListener {
            UiKit.showSnackbar(this, "Item deleted", "Undo") {
                UiKit.showToast(this, "Undo clicked!")
            }
        }

        val rootView = findViewById<View>(R.id.main)

        findViewById<Button>(R.id.btnErrorSnackbar).setOnClickListener {
            UiKit.showErrorSnackbar(rootView, "Something went wrong!")
        }

        findViewById<Button>(R.id.btnSuccessSnackbar).setOnClickListener {
            UiKit.showSuccessSnackbar(rootView, "Operation successful!")
        }
    }

    // UiKit - Clipboard & View

    private fun setupUiKitClipboardView() {
        findViewById<Button>(R.id.btnCopy).setOnClickListener {
            UiKit.copyToClipboard(this, "Hello from Toolbox!")
        }

        findViewById<Button>(R.id.btnPaste).setOnClickListener {
            val text = UiKit.getClipboardText(this)
            if (text != null) {
                DialogKit.showAlertDialog(this, "Clipboard Content", text)
            } else {
                UiKit.showToast(this, "Clipboard is empty")
            }
        }

        val demoView = findViewById<View>(R.id.demoView)
        var isVisible = true

        findViewById<Button>(R.id.btnToggleView).setOnClickListener {
            isVisible = !isVisible
            UiKit.showOrHide(demoView, isVisible)
            UiKit.showToast(this, if (isVisible) "View shown" else "View hidden")
        }
    }

    // UiKit - Validation

    private fun setupUiKitValidation() {
        findViewById<Button>(R.id.btnValidateEmail).setOnClickListener {
            DialogKit.showInputDialog(
                context = this,
                title = "Email Validation",
                hint = "Enter email address",
                onSubmit = { email ->
                    val isValid = UiKit.isEmailValid(email)
                    UiKit.showToast(this, if (isValid) "✓ Valid email" else "✗ Invalid email")
                }
            )
        }

        findViewById<Button>(R.id.btnValidatePhone).setOnClickListener {
            DialogKit.showInputDialog(
                context = this,
                title = "Phone Validation",
                hint = "Enter phone number",
                onSubmit = { phone ->
                    val isValid = UiKit.isPhoneValid(phone)
                    UiKit.showToast(this, if (isValid) "✓ Valid phone" else "✗ Invalid phone")
                }
            )
        }

        findViewById<Button>(R.id.btnValidateUrl).setOnClickListener {
            DialogKit.showInputDialog(
                context = this,
                title = "URL Validation",
                hint = "Enter URL",
                prefill = "https://",
                onSubmit = { url ->
                    val isValid = UiKit.isUrlValid(url)
                    UiKit.showToast(this, if (isValid) "✓ Valid URL" else "✗ Invalid URL")
                }
            )
        }

        findViewById<Button>(R.id.btnHideKeyboard).setOnClickListener {
            UiKit.hideKeyboard(this)
            UiKit.showToast(this, "Keyboard hidden")
        }
    }

    // IntentKit - Share & Web

    private fun setupIntentKitShareWeb() {
        findViewById<Button>(R.id.btnShareText).setOnClickListener {
            IntentKit.shareText(this, "Check out the Toolbox library for Android!")
        }

        findViewById<Button>(R.id.btnOpenWeb).setOnClickListener {
            IntentKit.openWebPage(this, "https://github.com/elytelabs/Toolbox")
        }

        findViewById<Button>(R.id.btnSendEmail).setOnClickListener {
            IntentKit.sendEmail(
                context = this,
                emails = arrayOf("contact@example.com"),
                subject = "Hello from Toolbox",
                body = "This email was sent using IntentKit!"
            )
        }

        findViewById<Button>(R.id.btnFeedbackEmail).setOnClickListener {
            IntentKit.sendFeedbackEmail(
                context = this,
                emails = arrayOf("support@elytelabs.com"),
                appName = "Toolbox Demo"
            )
        }
    }

    // IntentKit - Settings & App

    private fun setupIntentKitSettings() {
        findViewById<Button>(R.id.btnPlayStore).setOnClickListener {
            IntentKit.openPlayStore(this)
        }

        findViewById<Button>(R.id.btnAppSettings).setOnClickListener {
            IntentKit.openAppSettings(this)
        }

        findViewById<Button>(R.id.btnNotificationSettings).setOnClickListener {
            IntentKit.openNotificationSettings(this)
        }

        findViewById<Button>(R.id.btnAppVersion).setOnClickListener {
            val name = IntentKit.getAppVersionName(this)
            val code = IntentKit.getAppVersionCode(this)
            DialogKit.showAlertDialog(this, "App Version", "Name: $name\nCode: $code")
        }
    }

    // DialogKit - Alerts

    private fun setupDialogKitAlerts() {
        findViewById<Button>(R.id.btnAlertDialog).setOnClickListener {
            DialogKit.showAlertDialog(
                context = this,
                title = "Alert Dialog",
                message = "This is a simple alert dialog with an OK button."
            )
        }

        findViewById<Button>(R.id.btnConfirmDialog).setOnClickListener {
            DialogKit.showConfirmDialog(
                context = this,
                title = "Confirm Action",
                message = "Are you sure you want to proceed?",
                onConfirm = { UiKit.showToast(this, "Confirmed!") },
                onCancel = { UiKit.showToast(this, "Cancelled") }
            )
        }

        findViewById<Button>(R.id.btnThreeButtonDialog).setOnClickListener {
            DialogKit.showThreeButtonDialog(
                context = this,
                title = "Save Changes?",
                message = "You have unsaved changes.",
                positiveText = "Save",
                negativeText = "Discard",
                neutralText = "Cancel",
                onPositive = { UiKit.showToast(this, "Saved!") },
                onNegative = { UiKit.showToast(this, "Discarded") },
                onNeutral = { UiKit.showToast(this, "Cancelled") }
            )
        }

        findViewById<Button>(R.id.btnInputDialog).setOnClickListener {
            DialogKit.showInputDialog(
                context = this,
                title = "Enter Name",
                hint = "Your name",
                onSubmit = { name ->
                    UiKit.showToast(this, "Hello, $name!")
                }
            )
        }
    }

    // DialogKit - Lists & Loading

    private fun setupDialogKitLists() {
        val options = arrayOf("Option A", "Option B", "Option C", "Option D")

        findViewById<Button>(R.id.btnListDialog).setOnClickListener {
            DialogKit.showListDialog(this, "Select Option", options) { index, item ->
                UiKit.showToast(this, "Selected: $item (index $index)")
            }
        }

        findViewById<Button>(R.id.btnSingleChoiceDialog).setOnClickListener {
            DialogKit.showSingleChoiceDialog(
                context = this,
                title = "Choose One",
                items = options,
                selectedIndex = 0
            ) { _, item ->
                UiKit.showToast(this, "Chose: $item")
            }
        }

        findViewById<Button>(R.id.btnMultiChoiceDialog).setOnClickListener {
            DialogKit.showMultiChoiceDialog(this, "Select Multiple", options) { _, items ->
                UiKit.showToast(this, "Selected ${items.size} items: ${items.joinToString()}")
            }
        }

        findViewById<Button>(R.id.btnLoadingDialog).setOnClickListener {
            // Show custom loading dialog with our layout
            val customView = layoutInflater.inflate(R.layout.dialog_loading, null)
            val dialog = DialogKit.showLoadingDialog(this, customView)
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                dialog.dismiss()
                UiKit.showToast(this, "Export complete!")
            }, 2000)
        }
    }
}