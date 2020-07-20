package com.anuphap.androidexam.data.repository

import com.anuphap.androidexam.data.model.Product
import com.anuphap.androidexam.data.model.toProduct
import com.anuphap.androidexam.data.remote.ProductsDataSource
import io.reactivex.Single

class ProductsRepositoryImpl(private val productsDataSource: ProductsDataSource) :
    ProductsRepository {
    override fun retrieveProducts(): Single<List<Product>> {
        return productsDataSource.retrieveProducts().map {
            it.map { productResponse ->
                productResponse.toProduct()
            }
        }
    }
}
