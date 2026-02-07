package com.elytelabs.toolbox

import android.content.Context
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// <-- Alert Dialogs -->

/**
 * Shows a simple alert dialog with OK button.
 */
fun Context.showAlertDialog(
    title: String,
    message: String,
    positiveText: String = "OK",
    onPositive: (() -> Unit)? = null
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveText) { dialog, _ ->
            onPositive?.invoke()
            dialog.dismiss()
        }
        .show()
}

/**
 * Shows a confirmation dialog with positive and negative buttons.
 */
fun Context.showConfirmDialog(
    title: String,
    message: String,
    positiveText: String = "Yes",
    negativeText: String = "No",
    onConfirm: () -> Unit,
    onCancel: (() -> Unit)? = null
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveText) { dialog, _ ->
            onConfirm()
            dialog.dismiss()
        }
        .setNegativeButton(negativeText) { dialog, _ ->
            onCancel?.invoke()
            dialog.dismiss()
        }
        .show()
}

/**
 * Shows a dialog with three buttons.
 */
fun Context.showThreeButtonDialog(
    title: String,
    message: String,
    positiveText: String,
    negativeText: String,
    neutralText: String,
    onPositive: () -> Unit,
    onNegative: (() -> Unit)? = null,
    onNeutral: (() -> Unit)? = null
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveText) { dialog, _ ->
            onPositive()
            dialog.dismiss()
        }
        .setNegativeButton(negativeText) { dialog, _ ->
            onNegative?.invoke()
            dialog.dismiss()
        }
        .setNeutralButton(neutralText) { dialog, _ ->
            onNeutral?.invoke()
            dialog.dismiss()
        }
        .show()
}

// <-- Input Dialogs -->

/**
 * Shows a dialog with text input.
 */
fun Context.showInputDialog(
    title: String,
    hint: String = "",
    prefill: String = "",
    positiveText: String = "OK",
    negativeText: String = "Cancel",
    inputType: Int = InputType.TYPE_CLASS_TEXT,
    onSubmit: (String) -> Unit,
    onCancel: (() -> Unit)? = null
) {
    val editText = EditText(this).apply {
        this.hint = hint
        this.inputType = inputType
        setText(prefill)
        setSelection(prefill.length)
    }
    
    val container = FrameLayout(this).apply {
        val padding = (16 * resources.displayMetrics.density).toInt()
        setPadding(padding, padding / 2, padding, 0)
        addView(editText)
    }
    
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setView(container)
        .setPositiveButton(positiveText) { dialog, _ ->
            onSubmit(editText.text.toString())
            dialog.dismiss()
        }
        .setNegativeButton(negativeText) { dialog, _ ->
            onCancel?.invoke()
            dialog.dismiss()
        }
        .show()
}

// <-- List Dialogs -->

/**
 * Shows a single choice list dialog.
 */
fun Context.showListDialog(
    title: String,
    items: Array<String>,
    onSelect: (Int, String) -> Unit
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setItems(items) { _, which ->
            onSelect(which, items[which])
        }
        .show()
}

/**
 * Shows a single choice dialog with radio buttons.
 */
fun Context.showSingleChoiceDialog(
    title: String,
    items: Array<String>,
    selectedIndex: Int = -1,
    positiveText: String = "OK",
    onSelect: (Int, String) -> Unit
) {
    var selected = selectedIndex
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setSingleChoiceItems(items, selectedIndex) { _, which ->
            selected = which
        }
        .setPositiveButton(positiveText) { dialog, _ ->
            if (selected >= 0) {
                onSelect(selected, items[selected])
            }
            dialog.dismiss()
        }
        .setNegativeButton("Cancel", null)
        .show()
}

/**
 * Shows a multi-choice dialog with checkboxes.
 */
fun Context.showMultiChoiceDialog(
    title: String,
    items: Array<String>,
    checkedItems: BooleanArray? = null,
    positiveText: String = "OK",
    onSelect: (List<Int>, List<String>) -> Unit
) {
    val checked = checkedItems ?: BooleanArray(items.size) { false }
    
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMultiChoiceItems(items, checked) { _, which, isChecked ->
            checked[which] = isChecked
        }
        .setPositiveButton(positiveText) { dialog, _ ->
            val selectedIndices = checked.indices.filter { checked[it] }
            val selectedItems = selectedIndices.map { items[it] }
            onSelect(selectedIndices, selectedItems)
            dialog.dismiss()
        }
        .setNegativeButton("Cancel", null)
        .show()
}

// <-- Loading Dialogs -->

/**
 * Shows a loading dialog with a custom view layout.
 * Use this for full control over styling.
 *
 * @param customView Your custom layout (e.g., layoutInflater.inflate(R.layout.dialog_progress, null))
 * @param message Optional message to display alongside the custom view
 * @param cancelable Whether the dialog can be dismissed by tapping outside
 */
fun Context.showLoadingDialog(
    customView: View,
    message: String? = null,
    cancelable: Boolean = false
): AlertDialog {
    return MaterialAlertDialogBuilder(this)
        .setView(customView)
        .apply { message?.let { setMessage(it) } }
        .setCancelable(cancelable)
        .show()
}

/**
 * Shows a simple loading dialog with message.
 * For custom styling, use the overload that accepts a custom View.
 */
fun Context.showLoadingDialog(
    message: String = "Loading...",
    cancelable: Boolean = false
): AlertDialog {
    val progressBar = ProgressBar(this).apply {
        isIndeterminate = true
    }
    
    val container = FrameLayout(this).apply {
        val padding = (24 * resources.displayMetrics.density).toInt()
        setPadding(padding, padding, padding, padding)
        addView(progressBar, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = android.view.Gravity.CENTER
        })
    }
    
    return MaterialAlertDialogBuilder(this)
        .setMessage(message)
        .setView(container)
        .setCancelable(cancelable)
        .show()
}
