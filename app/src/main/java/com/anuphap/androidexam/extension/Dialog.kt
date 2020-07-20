package com.anuphap.androidexam.extension

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun Activity.showCommonDialog(
    message: String,
    onClick: DialogInterface.OnClickListener? = null
) {
    if (isDestroyed || isFinishing) return

    AlertDialog.Builder(this)
        .setMessage(message)
        .setPositiveButton(getString(android.R.string.ok), onClick)
        .show()
}