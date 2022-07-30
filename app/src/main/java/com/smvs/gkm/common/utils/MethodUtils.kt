package com.smvs.gkm.common.utils

import android.app.Activity
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.NavOptions
import com.smvs.gkm.BuildConfig
import com.smvs.gkm.R
import com.smvs.gkm.common.enums.NetworkStatus
import com.smvs.gkm.ui.nointernet.NoInternetActivity


fun isApiCallRequired(currentItem: Int, totalItem: Int): Boolean {
    return totalItem > currentItem
}

fun log(message: Throwable?) {
    if (message?.message == null)
        return
    if (BuildConfig.DEBUG)
        Log.e("ERROR" + " -- TAG --> ", message.message!!)
}

fun buildAnimOptions(
    popupTo: Int?,
    inclusive: Boolean = false
): NavOptions {
    val builder = NavOptions.Builder()
        .setEnterAnim(R.anim.right_in)
        .setExitAnim(R.anim.left_out)
        .setPopEnterAnim(R.anim.left_in)
        .setPopExitAnim(R.anim.right_out)
    if (popupTo != null) builder.setPopUpTo(popupTo, inclusive)
    return builder.build()
}

fun pxToDp(context: Context, px: Float): Float {
    return px / context.resources.displayMetrics.density
}

fun dpToPx(context: Context, dp: Float): Float {
    return dp * context.resources.displayMetrics.density
}

fun getDeviceWidth(context: Context): Int {
    val windowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun showHidePassword(editText: AppCompatEditText,imageView : AppCompatImageView){
    if(editText.transformationMethod.equals(PasswordTransformationMethod.getInstance())){
        imageView.setImageResource(R.drawable.ic_eye_inactive)
        //Show Password
        editText.transformationMethod = HideReturnsTransformationMethod.getInstance();
        editText.text?.length?.let {
            editText.setSelection(it)
        }
    }
    else{
        imageView.setImageResource(R.drawable.ic_eye);
        //Hide Password
        editText.transformationMethod = PasswordTransformationMethod.getInstance();
        editText.text?.length?.let {
            editText.setSelection(it)
        }
    }
}

fun checkNetworkConnection(activity: Activity, networkStatus: NetworkStatus) {
    if (networkStatus == NetworkStatus.NETWORK_OFF) {
        activity.startActivity(Intent(activity, NoInternetActivity::class.java))
    } else {
        if (activity is NoInternetActivity) activity.finish()
    }
}