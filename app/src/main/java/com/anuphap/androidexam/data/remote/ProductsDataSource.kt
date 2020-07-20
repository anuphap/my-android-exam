package com.anuphap.androidexam.data.remote

import com.anuphap.androidexam.data.model.ProductResponse
import io.reactivex.Single

interface ProductsDataSource {
    fun retrieveProducts(): Single<List<ProductResponse>>
}
