package com.anuphap.androidexam.di

import com.anuphap.androidexam.data.remote.ProductDataSourceImpl
import com.anuphap.androidexam.data.remote.ProductsDataSource
import com.anuphap.androidexam.data.repository.ProductsRepository
import com.anuphap.androidexam.data.repository.ProductsRepositoryImpl
import com.anuphap.androidexam.data.service.ServiceFactory
import org.koin.dsl.bind
import org.koin.dsl.module

object DataModule {
    val dataModule = module {
        factory { ProductsRepositoryImpl(productsDataSource = get()) } bind ProductsRepository::class
    }

    val remoteModule = module {
        factory { ServiceFactory.makeEndpointService() }
        single { ProductDataSourceImpl(endpointService = get()) } bind ProductsDataSource::class
    }
}
