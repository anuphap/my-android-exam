package com.anuphap.androidexam.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val image: String,
    val content: String,
    val isNewProduct: Boolean,
    val price: Double
) : Parcelable

fun ProductResponse.toProduct(): Product {
    return Product(
        id ?: 0,
        title ?: "",
        image ?: "",
        content ?: "",
        isNewProduct ?: false,
        price ?: 0.0
    )
}
