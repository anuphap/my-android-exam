package com.anuphap.androidexam.view.products

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anuphap.androidexam.R
import com.anuphap.androidexam.data.Result
import com.anuphap.androidexam.data.model.Product
import com.anuphap.androidexam.extension.hide
import com.anuphap.androidexam.extension.show
import com.anuphap.androidexam.extension.showCommonDialog
import com.anuphap.androidexam.view.BaseActivity
import com.anuphap.androidexam.view.productDetail.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_products.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsActivity : BaseActivity() {
    private val productsViewModel: ProductsViewModel by viewModel()
    private val productsAdapter by lazy {
        ProductsAdapter { product: Product, sharedElements: ArrayList<Pair<View, String>> ->
            ProductDetailActivity.startActivity(this, product, sharedElements)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.products_title)
        initView()
        loadProducts()
    }

    override fun getLayoutResource() = R.layout.activity_products

    override fun getRootView(): View = containerLayout

    override fun getViewModel() = productsViewModel

    private fun initView() {
        rvProducts?.apply {
            val gridLayout = GridLayoutManager(
                context,
                ProductsAdapter.GRID_SPAN_FULL_SIZE,
                RecyclerView.VERTICAL,
                false
            )
            gridLayout.spanSizeLookup = productsAdapter.spanSize
            layoutManager = gridLayout
            adapter = productsAdapter
        }

        btnReload.setOnClickListener {
            productsViewModel.retrieveProducts()
        }
    }

    private fun loadProducts() {
        productsViewModel.products.observe(this, Observer {
            when (it) {
                is Result.Success<List<Product>> -> {
                    handleResultSuccess(it.data)
                    hideLoading()
                }
                is Result.Error -> {
                    handleResultError(it.throwable)
                    hideLoading()
                }
                is Result.Loading -> {
                    showLoading()
                    hideEmptyView()
                }
            }
        })
    }

    private fun handleResultSuccess(products: List<Product>) {
        if (products.isNotEmpty()) {
            hideEmptyView()
            val items = arrayListOf<ProductsItemView>()
            items.add(ProductsItemView.HeaderView(getString(R.string.products_picking_label)))
            items.addAll(products.map { product -> ProductsItemView.ProductView(product) })
            productsAdapter.items = items
        } else {
            showEmptyView()
        }
    }

    private fun handleResultError(throwable: Throwable) {
        showCommonDialog(
            throwable.localizedMessage ?: getString(R.string.error_something_went_wrong)
        )
        showEmptyView()
    }

    private fun showLoading() {
        loadingProgressBar.show()
    }

    private fun hideLoading() {
        loadingProgressBar.hide()
    }

    private fun showEmptyView() {
        animationView.playAnimation()
        emptyLayout.show()
    }

    private fun hideEmptyView() {
        animationView.pauseAnimation()
        emptyLayout.hide()
    }
}
