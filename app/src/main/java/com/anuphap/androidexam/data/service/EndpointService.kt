package com.anuphap.androidexam.data.service

import com.anuphap.androidexam.data.model.ProductResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface EndpointService {
    @GET("products")
    fun getProducts(): Single<List<ProductResponse>>

    @GET("products/{productId}")
    fun getProduct(@Path("productId") productId: Int): Single<ProductResponse>
}
