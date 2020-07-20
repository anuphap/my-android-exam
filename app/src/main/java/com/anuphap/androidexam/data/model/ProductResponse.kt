package com.anuphap.androidexam.data.model

data class ProductResponse(
    var id: Int? = 0,
    var title: String? = "",
    var image: String? = "",
    var content: String? = "",
    var isNewProduct: Boolean? = false,
    var price: Double? = 0.0
)
