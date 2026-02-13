# Toolbox

[![Release](https://jitpack.io/v/elytelabs/toolbox.svg)](https://jitpack.io/#elytelabs/toolbox)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

A modern Kotlin utility library for Android. Write less boilerplate, build faster.

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

```kotlin
// Random color
val color = ColorGenerator.getRandomColor()

// Avatar color (consistent for same input)
val avatarColor = ColorGenerator.getColorForString("john@email.com")

// Color with alpha (0-255)
val transparentColor = ColorGenerator.getRandomColorWithAlpha(128)

// Get color at specific index
val colorAt = ColorGenerator.getColorAt(5)

// Get all colors
val allColors = ColorGenerator.getColorList()
```

---

### UiKit

#### Toast

```kotlin
UiKit.showToast(context, "Hello!")
UiKit.showLongToast(context, "Longer message")
```

#### Snackbar

```kotlin
UiKit.showSnackbar(activity, "Message saved")
UiKit.showSnackbar(activity, "Item deleted", "Undo") { /* undo action */ }
UiKit.showErrorSnackbar(view, "Something went wrong!")
UiKit.showSuccessSnackbar(view, "Upload complete!")

// Custom colors
UiKit.showErrorSnackbar(view, "Error!", backgroundColor = 0xFFE53935.toInt())
UiKit.showSuccessSnackbar(view, "Done!", backgroundColor = 0xFF43A047.toInt())
```

#### Clipboard

```kotlin
UiKit.copyToClipboard(context, "Text to copy")
val text = UiKit.getClipboardText(context)
```

#### View Visibility

```kotlin
UiKit.show(view)
UiKit.hide(view)          // GONE
UiKit.invisible(view)     // INVISIBLE
UiKit.showOrHide(view, isLoggedIn)
```

#### Keyboard

```kotlin
UiKit.hideKeyboard(activity)
UiKit.showKeyboard(editText)
UiKit.hideKeyboard(view)
```

#### Validation

```kotlin
UiKit.isEmailValid("test@example.com")  // true
UiKit.isPhoneValid("+1234567890")       // true
UiKit.isUrlValid("https://google.com")  // true
```

---

### IntentKit

#### Share

```kotlin
IntentKit.shareText(context, "Check out this app!")
IntentKit.shareImage(context, imageUri, "Caption")
IntentKit.shareImages(context, arrayListOf(uri1, uri2))
```

#### Web & Email

```kotlin
IntentKit.openWebPage(context, "https://google.com")
IntentKit.openUriWithFallback(context, "market://details?id=com.example", "https://play.google.com/store/apps/details?id=com.example")
IntentKit.sendEmail(context, arrayOf("support@app.com"), "Subject", "Body")
IntentKit.sendFeedbackEmail(context, arrayOf("support@app.com"), "MyApp")
// Subject: "Feedback for MyApp v 1.0.0"
```

#### App & Settings

```kotlin
IntentKit.openPlayStore(context)
IntentKit.openAppSettings(context)
IntentKit.openNotificationSettings(context)
IntentKit.openLocationSettings(context)

val versionName = IntentKit.getAppVersionName(context)
val versionCode = IntentKit.getAppVersionCode(context)
```

---

### DialogKit

#### Alert Dialogs

```kotlin
DialogKit.showAlertDialog(context, "Title", "Message")

DialogKit.showConfirmDialog(
    context = context,
    title = "Delete?",
    message = "This cannot be undone.",
    onConfirm = { delete() },
    onCancel = { /* optional */ }
)

DialogKit.showThreeButtonDialog(
    context = context,
    title = "Save Changes?",
    message = "You have unsaved changes.",
    positiveText = "Save",
    negativeText = "Discard",
    neutralText = "Cancel",
    onPositive = { save() },
    onNegative = { discard() }
)
```

#### Input Dialog

```kotlin
DialogKit.showInputDialog(
    context = context,
    title = "Enter Name",
    hint = "Your name",
    onSubmit = { name -> /* handle */ }
)
```

#### List Dialogs

```kotlin
val options = arrayOf("A", "B", "C")

DialogKit.showListDialog(context, "Select", options) { index, item -> }

DialogKit.showSingleChoiceDialog(context, "Choose", options, selectedIndex = 0) { index, item -> }

DialogKit.showMultiChoiceDialog(context, "Select", options) { indices, items -> }
```

#### Loading Dialog

```kotlin
val dialog = DialogKit.showLoadingDialog(context, "Please wait...")
// Later: dialog.dismiss()

// With custom view
val customView = layoutInflater.inflate(R.layout.dialog_progress, null)
val dialog = DialogKit.showLoadingDialog(context, customView, "Exporting...")
```

---

## Requirements

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Language**: Kotlin

---

## License

```
Copyright 2026 ElyteLabs

Licensed under the Apache License, Version 2.0
```
