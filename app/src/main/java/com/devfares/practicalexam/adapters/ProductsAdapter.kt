package com.devfares.practicalexam.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.devfares.practicalexam.databinding.ItemProductApiBinding
import com.devfares.practicalexam.data.model.ProductModel


class ProductsAdapter(
    private var element: List<ProductModel>,
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    class ProductViewHolder(
        private val binding: ItemProductApiBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(element: ProductModel) {
            binding.productName.text = if (element.title.length > 26) {
                element.title.substring(0, 26) + "..."
            } else element.title


            binding.productPrice.text = element.price.toString()+"$"
            binding.productCategory.text = element.category
            Glide.with(binding.root.context)
                .load(element.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(android.R.drawable.stat_notify_error)
                .into(binding.productImage)
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProductViewHolder {
        val binding =
            ItemProductApiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = element[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return element.size
    }

    fun updateData(newData: List<ProductModel>) {
        element = newData
        notifyDataSetChanged()
    }
}