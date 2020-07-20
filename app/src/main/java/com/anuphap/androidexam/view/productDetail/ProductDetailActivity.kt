package com.anuphap.androidexam.view.productDetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.anuphap.androidexam.R
import com.anuphap.androidexam.data.model.Product
import com.anuphap.androidexam.extension.setProductImage
import com.anuphap.androidexam.extension.toDisplayPrice
import com.anuphap.androidexam.view.BaseActivity
import com.anuphap.androidexam.view.BaseViewModel
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.util.Pair as UtilPair

class ProductDetailActivity : BaseActivity() {
    private val viewModel: ProductDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.product_detail_title)
        setDisplayHomeEnabled(true)
        observeProduct()
        intent.getParcelableExtra<Product>(EXTRA_PRODUCT)?.let {
            viewModel.setProduct(it)
        }
    }

    override fun getLayoutResource(): Int = R.layout.activity_product_detail

    override fun getRootView(): View = containerLayout

    override fun getViewModel(): BaseViewModel = viewModel

    private fun observeProduct() {
        viewModel.product.observe(this, Observer {
            updateProductView(it)
        })
    }

    private fun updateProductView(product: Product) {
        ivProductImage.setProductImage(product.image)
        tvProductNew.visibility = if (product.isNewProduct) View.VISIBLE else View.GONE
        tvProductNew.text = if (product.isNewProduct) getString(R.string.product_new) else ""
        tvProductName.text = product.title
        tvProductPrice.text = product.price.toDisplayPrice()
        tvProductContent.text = product.content
    }

    companion object {
        private const val EXTRA_PRODUCT = "EXTRA_PRODUCT"

        fun startActivity(
            activity: Activity, product: Product, sharedElements: ArrayList<Pair<View, String>>
        ) {
            val intent = Intent(activity, ProductDetailActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT, product)
            val pair1 =
                UtilPair.create(sharedElements[0].first, sharedElements[0].second) // product new
            val pair2 =
                UtilPair.create(sharedElements[1].first, sharedElements[1].second) // product name
            val pair3 =
                UtilPair.create(sharedElements[2].first, sharedElements[2].second) // product image
            val pair4 =
                UtilPair.create(sharedElements[3].first, sharedElements[3].second) // product price
            val activityOptions = if (product.isNewProduct) {
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    pair1,
                    pair2,
                    pair3,
                    pair4
                )
            } else {
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair2, pair3, pair4)
            }

            activity.startActivity(intent, activityOptions.toBundle())
        }
    }
}
