package com.anuphap.androidexam.view

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

enum class NetworkState { CONNECTED, DISCONNECTED }

open class BaseViewModel(private val context: Context) : ViewModel() {
    private val disposables: CompositeDisposable = CompositeDisposable()
    private var _networkState: MutableLiveData<NetworkState> = MutableLiveData()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    init {
        startObserveNetwork()
    }

    private fun startObserveNetwork() {
        val connectivityDisposable = ReactiveNetwork.observeNetworkConnectivity(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connectivity ->
                if (_networkState.value != null ||
                    (connectivity.state().name == NetworkState.DISCONNECTED.toString())
                ) {
                    _networkState.value =
                        if (connectivity.state().name == NetworkState.CONNECTED.toString()) {
                            NetworkState.CONNECTED
                        } else {
                            NetworkState.DISCONNECTED
                        }
                }
            }
        addToDisposable(connectivityDisposable)
    }

    fun addToDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
