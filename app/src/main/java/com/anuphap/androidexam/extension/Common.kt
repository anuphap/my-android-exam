package com.anuphap.androidexam.extension

fun Double.toDisplayPrice(): String {
    return String.format("%.2f", this)
}