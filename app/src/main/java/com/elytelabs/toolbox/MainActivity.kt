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

    // <-- ColorGenerator Examples -->
    private fun setupColorGenerator() {
        val demoView = findViewById<View>(R.id.demoView)

        findViewById<Button>(R.id.btnRandomColor).setOnClickListener {
            val color = ColorGenerator.getRandomColor()
            demoView.setBackgroundColor(color)
            showToast("Random color applied!")
        }

        findViewById<Button>(R.id.btnAvatarColor).setOnClickListener {
            showInputDialog(
                title = "Avatar Color",
                hint = "Enter name or email",
                onSubmit = { input ->
                    val color = ColorGenerator.getColorForString(input)
                    demoView.setBackgroundColor(color)
                    showToast("Avatar color for: $input")
                }
            )
        }
    }

    // <-- UiKit - Toast & Snackbar -->
    private fun setupUiKitToastSnackbar() {
        findViewById<Button>(R.id.btnShortToast).setOnClickListener {
            showToast("This is a short toast")
        }

        findViewById<Button>(R.id.btnLongToast).setOnClickListener {
            showLongToast("This is a longer toast message that stays visible for longer")
        }

        findViewById<Button>(R.id.btnSnackbar).setOnClickListener {
            showSnackbar("This is a snackbar message")
        }

        findViewById<Button>(R.id.btnSnackbarAction).setOnClickListener {
            showSnackbar(
                message = "Item deleted",
                actionText = "Undo"
            ) {
                showToast("Undo clicked!")
            }
        }

        val rootView = findViewById<View>(R.id.main)

        findViewById<Button>(R.id.btnErrorSnackbar).setOnClickListener {
            rootView.showErrorSnackbar("Something went wrong!")
        }

        findViewById<Button>(R.id.btnSuccessSnackbar).setOnClickListener {
            rootView.showSuccessSnackbar("Operation successful!")
        }
    }

    // <-- UiKit - Clipboard & View -->
    private fun setupUiKitClipboardView() {
        findViewById<Button>(R.id.btnCopy).setOnClickListener {
            copyToClipboard("Hello from Toolbox!")
        }

        findViewById<Button>(R.id.btnPaste).setOnClickListener {
            val text = getClipboardText()
            if (text != null) {
                showAlertDialog("Clipboard Content", text)
            } else {
                showToast("Clipboard is empty")
            }
        }

        val demoView = findViewById<View>(R.id.demoView)
        var isVisible = true

        findViewById<Button>(R.id.btnToggleView).setOnClickListener {
            isVisible = !isVisible
            demoView.showOrHide(isVisible)
            showToast(if (isVisible) "View shown" else "View hidden")
        }
    }

    // <-- UiKit - Validation -->
    private fun setupUiKitValidation() {
        findViewById<Button>(R.id.btnValidateEmail).setOnClickListener {
            showInputDialog(
                title = "Email Validation",
                hint = "Enter email address",
                onSubmit = { email ->
                    val isValid = email.isEmailValid()
                    if (isValid) {
                        showToast("✓ Valid email")
                    } else {
                        showToast("✗ Invalid email")
                    }
                }
            )
        }

        findViewById<Button>(R.id.btnValidatePhone).setOnClickListener {
            showInputDialog(
                title = "Phone Validation",
                hint = "Enter phone number",
                onSubmit = { phone ->
                    val isValid = phone.isPhoneValid()
                    if (isValid) {
                        showToast("✓ Valid phone")
                    } else {
                        showToast("✗ Invalid phone")
                    }
                }
            )
        }

        findViewById<Button>(R.id.btnValidateUrl).setOnClickListener {
            showInputDialog(
                title = "URL Validation",
                hint = "Enter URL",
                prefill = "https://",
                onSubmit = { url ->
                    val isValid = url.isUrlValid()
                    if (isValid) {
                        showToast("✓ Valid URL")
                    } else {
                        showToast("✗ Invalid URL")
                    }
                }
            )
        }

        findViewById<Button>(R.id.btnHideKeyboard).setOnClickListener {
            hideKeyboard()
            showToast("Keyboard hidden")
        }
    }

    // <-- IntentKit - Share & Web -->
    private fun setupIntentKitShareWeb() {
        findViewById<Button>(R.id.btnShareText).setOnClickListener {
            shareText("Check out the Toolbox library for Android!")
        }

        findViewById<Button>(R.id.btnOpenWeb).setOnClickListener {
            openWebPage("https://github.com/elytelabs/Toolbox")
        }

        findViewById<Button>(R.id.btnSendEmail).setOnClickListener {
            sendEmail(
                emails = arrayOf("contact@example.com"),
                subject = "Hello from Toolbox",
                body = "This email was sent using IntentKit!"
            )
        }

        findViewById<Button>(R.id.btnFeedbackEmail).setOnClickListener {
            sendFeedbackEmail(
                emails = arrayOf("support@elytelabs.com"),
                appName = "Toolbox Demo"
            )
        }
    }

    // <-- IntentKit - Settings & App -->
    private fun setupIntentKitSettings() {
        findViewById<Button>(R.id.btnPlayStore).setOnClickListener {
            openPlayStore()
        }

        findViewById<Button>(R.id.btnAppSettings).setOnClickListener {
            openAppSettings()
        }

        findViewById<Button>(R.id.btnNotificationSettings).setOnClickListener {
            openNotificationSettings()
        }

        findViewById<Button>(R.id.btnAppVersion).setOnClickListener {
            val name = getAppVersionName()
            val code = getAppVersionCode()
            showAlertDialog("App Version", "Name: $name\nCode: $code")
        }
    }

    // <-- DialogKit - Alerts -->
    private fun setupDialogKitAlerts() {
        findViewById<Button>(R.id.btnAlertDialog).setOnClickListener {
            showAlertDialog(
                title = "Alert Dialog",
                message = "This is a simple alert dialog with an OK button."
            )
        }

        findViewById<Button>(R.id.btnConfirmDialog).setOnClickListener {
            showConfirmDialog(
                title = "Confirm Action",
                message = "Are you sure you want to proceed?",
                onConfirm = { showToast("Confirmed!") },
                onCancel = { showToast("Cancelled") }
            )
        }

        findViewById<Button>(R.id.btnThreeButtonDialog).setOnClickListener {
            showThreeButtonDialog(
                title = "Save Changes?",
                message = "You have unsaved changes.",
                positiveText = "Save",
                negativeText = "Discard",
                neutralText = "Cancel",
                onPositive = { showToast("Saved!") },
                onNegative = { showToast("Discarded") },
                onNeutral = { showToast("Cancelled") }
            )
        }

        findViewById<Button>(R.id.btnInputDialog).setOnClickListener {
            showInputDialog(
                title = "Enter Name",
                hint = "Your name",
                onSubmit = { name ->
                    showToast("Hello, $name!")
                }
            )
        }
    }

    // <-- DialogKit - Lists & Loading -->
    private fun setupDialogKitLists() {
        val options = arrayOf("Option A", "Option B", "Option C", "Option D")

        findViewById<Button>(R.id.btnListDialog).setOnClickListener {
            showListDialog(
                title = "Select Option",
                items = options
            ) { index, item ->
                showToast("Selected: $item (index $index)")
            }
        }

        findViewById<Button>(R.id.btnSingleChoiceDialog).setOnClickListener {
            showSingleChoiceDialog(
                title = "Choose One",
                items = options,
                selectedIndex = 0
            ) { index, item ->
                showToast("Chose: $item")
            }
        }

        findViewById<Button>(R.id.btnMultiChoiceDialog).setOnClickListener {
            showMultiChoiceDialog(
                title = "Select Multiple",
                items = options
            ) { indices, items ->
                showToast("Selected ${items.size} items: ${items.joinToString()}")
            }
        }

        findViewById<Button>(R.id.btnLoadingDialog).setOnClickListener {
            val dialog = showLoadingDialog("Please wait...")
            // Auto-dismiss after 2 seconds
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                dialog.dismiss()
                showToast("Loading complete!")
            }, 2000)
        }
    }
}