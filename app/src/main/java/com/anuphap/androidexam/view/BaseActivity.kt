package com.anuphap.androidexam.view

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.anuphap.androidexam.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_actionbar_base.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        initToolbar()
        observeNetworkState()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    protected abstract fun getRootView(): View

    protected abstract fun getViewModel(): BaseViewModel?

    private fun initToolbar() {
        baseToolbar?.let {
            setSupportActionBar(it)
        }
    }

    protected fun setDisplayHomeEnabled(enable: Boolean = true) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(enable)
            setDisplayShowHomeEnabled(enable)
        }
    }

    private fun observeNetworkState() {
        getViewModel()?.networkState?.observe(this, Observer {
            it ?: return@Observer

            when (it) {
                NetworkState.CONNECTED -> {
                    notify(getRootView(), R.string.network_connected)
                }
                NetworkState.DISCONNECTED -> {
                    notify(
                        getRootView(),
                        R.string.error_connection_lost,
                        Snackbar.LENGTH_INDEFINITE
                    )
                }
            }
        })
    }

    private fun notify(
        viewContainer: View,
        @StringRes message: Int,
        displayLength: Int = Snackbar.LENGTH_SHORT
    ) {
        Snackbar.make(viewContainer, message, displayLength).show()
    }
}
