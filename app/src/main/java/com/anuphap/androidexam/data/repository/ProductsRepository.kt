package com.anuphap.androidexam.data.repository

import com.anuphap.androidexam.data.model.Product
import io.reactivex.Single

interface ProductsRepository {
    fun retrieveProducts(): Single<List<Product>>
}
