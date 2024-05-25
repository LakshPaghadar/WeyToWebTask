package com.waytoweb.practicaltask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.waytoweb.practicaltask.databinding.RowFileProductListBinding
import com.waytoweb.practicaltask.network.response.Product

class ProductAdapter(
    var callbackForClick:(Int)->Unit,
    var callbackForEdit:(Product,Int)->Unit,
    var callbackForDelete:(Int,Int)->Unit
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var list= arrayListOf<Product>()
    inner class ViewHolder(var binding: RowFileProductListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowFileProductListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item=list[position]
        holder.binding.apply {
            textViewTitleValue.text=item.title
            textViewDescValue.text=item.description

            root.setOnClickListener {
                callbackForClick.invoke(item.id)
            }
            buttonEdit.setOnClickListener {
                callbackForEdit.invoke(item,position)
            }
            buttonDelete.setOnClickListener {
                callbackForDelete.invoke(item.id,position)
            }
        }
    }
}