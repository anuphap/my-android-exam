package com.anuphap.androidexam.view.products

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anuphap.androidexam.data.Result
import com.anuphap.androidexam.data.model.Product
import com.anuphap.androidexam.data.repository.ProductsRepository
import com.anuphap.androidexam.view.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ProductsViewModel(private val productsRepository: ProductsRepository, context: Context) :
    BaseViewModel(context) {
    private var _products: MutableLiveData<Result<List<Product>>> = MutableLiveData()
    val products: LiveData<Result<List<Product>>>
        get() = _products

    init {
        retrieveProducts()
    }

    fun retrieveProducts() {
        _products.postValue(Result.Loading)
        val single = productsRepository.retrieveProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        addToDisposable(single.subscribeWith(object : DisposableSingleObserver<List<Product>>() {
            override fun onSuccess(t: List<Product>) {
                _products.postValue(Result.Success(t))
            }

            override fun onError(e: Throwable) {
                _products.postValue(Result.Error(e))
            }
        }))
    }
}
