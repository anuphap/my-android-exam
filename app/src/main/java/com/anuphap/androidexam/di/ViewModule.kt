package com.anuphap.androidexam.di

import com.anuphap.androidexam.view.productDetail.ProductDetailViewModel
import com.anuphap.androidexam.view.products.ProductsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModule {
    val viewModule = module {
        viewModel { ProductsViewModel(context = androidContext(), productsRepository = get()) }
        viewModel { ProductDetailViewModel(context = androidContext()) }
    }
}
