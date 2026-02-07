# Toolbox

[![Release](https://jitpack.io/v/elytelabs/toolbox.svg)](https://jitpack.io/#elytelabs/toolbox)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

A modern Kotlin utility library for Android that provides clean extension functions for everyday tasks. Write less boilerplate, build faster.

## Features

| Module | Description |
|--------|-------------|
| **ColorGenerator** | Material color palette, random colors, avatar colors from strings |
| **UiKit** | Toast, Snackbar, Clipboard, View visibility, Keyboard, Validation |
| **IntentKit** | Share, Email, Web, PlayStore, App settings intents |
| **DialogKit** | Material dialogs - alert, confirm, input, list, single/multi choice, loading |

---

## Installation

### Step 1: Add JitPack repository

In your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add the dependency

In your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.elytelabs:toolbox:Tag")
}
```

Replace `Tag` with the latest release version.

---

## Usage

### ColorGenerator

Generate random Material Design colors or consistent avatar colors.

```kotlin
// Get a random color
val color = ColorGenerator.getRandomColor()
view.setBackgroundColor(color)

// Get consistent color for a string (great for avatars)
val avatarColor = ColorGenerator.getColorForString("john@email.com")
// Same string always returns same color

// Get color with custom alpha (0-255)
val transparentColor = ColorGenerator.getRandomColorWithAlpha(128)

// Get color at specific index
val colorAt = ColorGenerator.getColorAt(5)

// Get all available colors
val allColors = ColorGenerator.getColorList()
```

---

### UiKit

#### Toast

```kotlin
// Short toast
showToast("Hello!")

// Long toast
showLongToast("This message stays longer")
```

#### Snackbar

```kotlin
// Simple snackbar
showSnackbar("Message saved")

// Snackbar with action
showSnackbar("Item deleted", "Undo") {
    // Undo action
}

// Error snackbar (red background)
view.showErrorSnackbar("Something went wrong!")

// Success snackbar (green background)
view.showSuccessSnackbar("Upload complete!")
```

#### Clipboard

```kotlin
// Copy to clipboard (auto shows toast on Android 12 and below)
copyToClipboard("Text to copy")

// Get clipboard content
val text = getClipboardText()
```

#### View Visibility

```kotlin
// Show/hide/invisible
view.show()
view.hide()        // GONE
view.invisible()   // INVISIBLE (keeps layout space)

// Conditional toggle
view.showOrHide(isLoggedIn)
```

#### Keyboard

```kotlin
// Hide keyboard from activity
hideKeyboard()

// Show keyboard for a view
editText.showKeyboard()

// Hide keyboard from a view
editText.hideKeyboard()
```

#### Validation

```kotlin
// Email validation
"test@example.com".isEmailValid()  // true
"invalid-email".isEmailValid()     // false

// Phone validation
"+1234567890".isPhoneValid()       // true

// URL validation
"https://google.com".isUrlValid()  // true
```

---

### IntentKit

#### Share

```kotlin
// Share text
shareText("Check out this app!")

// Share image
shareImage(imageUri, "Caption text")

// Share multiple images
shareImages(arrayListOf(uri1, uri2), "Photos")
```

#### Web & Email

```kotlin
// Open URL in browser
openWebPage("https://elytelabs.vercel.app")

// Send email
sendEmail(
    emails = arrayOf("support@app.com"),
    subject = "Hello",
    body = "Message body"
)

// Send feedback email with device info
sendFeedbackEmail(
    emails = arrayOf("support@app.com"),
    appName = "MyApp"
)
// Auto-includes: app version, device model, Android version
```

#### App & Settings

```kotlin
// Open Play Store page
openPlayStore()

// Open app settings
openAppSettings()

// Open notification settings
openNotificationSettings()

// Open location settings
openLocationSettings()

// Get app version
val versionName = getAppVersionName()  // "1.0.0"
val versionCode = getAppVersionCode()  // 1
```

---

### DialogKit

#### Alert Dialogs

```kotlin
// Simple alert
showAlertDialog(
    title = "Alert",
    message = "This is an alert dialog"
)

// Confirmation dialog
showConfirmDialog(
    title = "Delete Item?",
    message = "This action cannot be undone.",
    onConfirm = { deleteItem() },
    onCancel = { /* optional */ }
)

// Three-button dialog
showThreeButtonDialog(
    title = "Save Changes?",
    message = "You have unsaved changes.",
    positiveText = "Save",
    negativeText = "Discard",
    neutralText = "Cancel",
    onPositive = { save() },
    onNegative = { discard() },
    onNeutral = { /* cancel */ }
)
```

#### Input Dialog

```kotlin
showInputDialog(
    title = "Enter Name",
    hint = "Your name",
    prefill = "John",
    inputType = InputType.TYPE_CLASS_TEXT
) { name ->
    showToast("Hello, $name!")
}
```

#### List Dialogs

```kotlin
val options = arrayOf("Option A", "Option B", "Option C")

// Simple list
showListDialog("Select", options) { index, item ->
    showToast("Selected: $item")
}

// Single choice with radio buttons
showSingleChoiceDialog(
    title = "Choose One",
    items = options,
    selectedIndex = 0
) { index, item ->
    // Handle selection
}

// Multi-choice with checkboxes
showMultiChoiceDialog(
    title = "Select Multiple",
    items = options,
    checkedItems = booleanArrayOf(false, true, false)
) { indices, items ->
    showToast("Selected: ${items.joinToString()}")
}
```

#### Loading Dialog

```kotlin
// Simple loading dialog
val dialog = showLoadingDialog("Please wait...")
// Later: dialog.dismiss()

// Custom view loading dialog
val customView = layoutInflater.inflate(R.layout.dialog_progress, null)
val dialog = showLoadingDialog(customView, message = "Exporting...")
```

---

## Requirements

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35
- **Language**: Kotlin

## Dependencies

The library uses the following dependencies:
- `androidx.core:core-ktx`
- `androidx.appcompat:appcompat`
- `com.google.android.material:material`

---

## License

```
Copyright 2024 ElyteLabs

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
