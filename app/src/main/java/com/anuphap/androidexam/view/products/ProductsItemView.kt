package com.anuphap.androidexam.view.products

import com.anuphap.androidexam.data.model.Product

sealed class ProductsItemView {
    abstract val viewType: Int

    data class HeaderView(
        val title: String,
        override val viewType: Int = ProductsAdapter.ITEM_HEADER_VIEW
    ) : ProductsItemView()

    data class ProductView(
        val product: Product,
        override val viewType: Int = ProductsAdapter.ITEM_PRODUCT_VIEW
    ) : ProductsItemView()
}