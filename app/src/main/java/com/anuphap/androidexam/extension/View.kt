package com.anuphap.androidexam.extension

import android.view.View
import android.widget.ImageView
import com.anuphap.androidexam.R
import com.bumptech.glide.Glide

fun ImageView.setProductImage(url: String) {
    Glide.with(this.context).load(url)
        .placeholder(R.drawable.ic_cookie_place_holder)
        .into(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}