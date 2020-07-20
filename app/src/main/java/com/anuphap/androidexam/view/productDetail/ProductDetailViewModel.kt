package com.anuphap.androidexam.view.productDetail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anuphap.androidexam.data.model.Product
import com.anuphap.androidexam.view.BaseViewModel

class ProductDetailViewModel(context: Context) : BaseViewModel(context) {
    private var _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product>
        get() = _product

    fun setProduct(product: Product) {
        this._product.postValue(product)
    }
}
