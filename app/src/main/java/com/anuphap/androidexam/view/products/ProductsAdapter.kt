package com.anuphap.androidexam.view.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anuphap.androidexam.R
import com.anuphap.androidexam.data.model.Product
import com.anuphap.androidexam.extension.setProductImage
import com.anuphap.androidexam.extension.toDisplayPrice
import kotlinx.android.synthetic.main.layout_item_header.view.*
import kotlinx.android.synthetic.main.layout_item_product.view.*

class ProductsAdapter(private val clickListener: (Product, ArrayList<Pair<View, String>>) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<ProductsItemView> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_HEADER_VIEW -> {
                HeaderViewHolder(inflater.inflate(R.layout.layout_item_header, parent, false))
            }

            ITEM_PRODUCT_VIEW -> {
                ProductViewHolder(inflater.inflate(R.layout.layout_item_product, parent, false))
            }

            else -> {
                throw Exception("No Found ViewType $viewType")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is HeaderViewHolder -> {
                holder.bind(item as ProductsItemView.HeaderView)
            }

            is ProductViewHolder -> {
                holder.bind(item as ProductsItemView.ProductView)
            }
        }
    }

    val spanSize: GridLayoutManager.SpanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (getItemViewType(position)) {
                ITEM_HEADER_VIEW -> GRID_SPAN_FULL_SIZE
                ITEM_PRODUCT_VIEW -> GRID_SPAN_HALF_SIZE
                else -> GRID_SPAN_FULL_SIZE
            }
        }
    }

    companion object {
        const val GRID_SPAN_FULL_SIZE = 2
        private const val GRID_SPAN_HALF_SIZE = GRID_SPAN_FULL_SIZE / 2

        const val ITEM_HEADER_VIEW = 1
        const val ITEM_PRODUCT_VIEW = 2
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.tvTitle
        fun bind(headerItem: ProductsItemView.HeaderView) {
            tvTitle.text = headerItem.title
        }
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvProductName = itemView.tvProductName
        private val ivProductImage = itemView.ivProductImage
        private val tvProductNew = itemView.tvProductNew
        private val tvProductPrice = itemView.tvProductPrice
        private val context = itemView.context

        fun bind(productItem: ProductsItemView.ProductView) {
            val product = productItem.product
            tvProductNew.visibility = if (product.isNewProduct) View.VISIBLE else View.GONE
            tvProductNew.text =
                if (product.isNewProduct) itemView.context.getString(R.string.product_new) else ""
            ivProductImage.setProductImage(product.image)
            tvProductName.text = product.title
            tvProductPrice.text = product.price.toDisplayPrice()

            itemView.setOnClickListener {
                clickListener(
                    productItem.product,
                    arrayListOf(
                        Pair(tvProductNew, context.getString(R.string.product_new_transition)),
                        Pair(tvProductName, context.getString(R.string.product_name_transition)),
                        Pair(ivProductImage, context.getString(R.string.product_image_transition)),
                        Pair(tvProductPrice, context.getString(R.string.product_price_transition))
                    )
                )
            }
        }
    }
}
