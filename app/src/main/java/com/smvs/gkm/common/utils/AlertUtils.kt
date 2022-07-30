package com.smvs.gkm.common.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.smvs.gkm.R

object AlertUtils {
    fun showAlert(
        context: Context,
        @StringRes message: Int,
        positiveButtonCallback: () -> Unit,
        negativeButtonCallback: () -> Unit = {}
    ) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.text_alert_title))
            .setMessage(message)
            .setPositiveButton(
                context.getString(R.string.text_yes)
            ) { _, _ ->
                positiveButtonCallback()
            }.setNegativeButton(context.getString(R.string.text_no)) { _, _ ->
                negativeButtonCallback()
            }.create().show()
    }
}