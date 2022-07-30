package com.smvs.gkm.common.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide


fun ImageView.loadImage(
    @DrawableRes
    imageResource: Int?
) {
    Glide.with(context)
        .load(imageResource)
        .into(this)
}