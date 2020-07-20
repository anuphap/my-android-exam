package com.anuphap.androidexam.data.remote

import com.anuphap.androidexam.data.model.ProductResponse
import com.anuphap.androidexam.data.service.EndpointService
import io.reactivex.Single

class ProductDataSourceImpl(private val endpointService: EndpointService) : ProductsDataSource {
    override fun retrieveProducts(): Single<List<ProductResponse>> {
        return endpointService.getProducts()
    }
}
