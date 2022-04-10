/**
 * Copyright 2020 Academia Móviles
 * https://www.academiamoviles.com/
 * @author : Eduardo Medina
 */
package com.emedinaa.kotlinapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.model.CartItem
import com.emedinaa.kotlinapp.model.OrderViewFooter
import com.emedinaa.kotlinapp.model.OrderViewType

/**
 * @author Eduardo Medina
 */
class OrderAdapter(var data: List<OrderViewType>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val HEADER = 0
        const val ITEM = 1
        const val FOOTER = 2


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER -> HeaderViewHolder(inflater.inflate(R.layout.row_cart_header, parent, false))
            ITEM -> ItemViewHolder(inflater.inflate(R.layout.row_cart_item, parent, false))
            FOOTER -> FooterViewHolder(inflater.inflate(R.layout.row_cart_footer, parent, false))
            else -> ItemViewHolder(inflater.inflate(R.layout.row_cart_item, parent, false))
        }
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        when (vh.itemViewType) {
            HEADER -> renderHeader()
            FOOTER -> renderFooter(vh as FooterViewHolder, position)
            else -> renderItem(vh as ItemViewHolder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        data = sortData(data)
        return when {
            data[position].isHeader() -> HEADER
            data[position].isFooter() -> FOOTER
            else -> ITEM
        }
    }

    override fun getItemCount(): Int = data.size

    private fun sortData(data: List<OrderViewType>): List<OrderViewType> {
        val header = data.filter { it.isHeader() }
        val footer = data.filter { it.isFooter() }
        val items = data.filter { it.isItem() }

        return header + items + footer
    }

    private fun renderFooter(footerViewHolder: FooterViewHolder, position: Int) {
        val orderViewFooter = data[position] as OrderViewFooter

        val total = "%.2f".format(orderViewFooter.total)
        footerViewHolder.textViewTotal.text = "S/.$total"

    }

    private fun renderHeader() {}

    private fun renderItem(itemViewHolder: ItemViewHolder, position: Int) {
        val item = data[position] as CartItem
        val price = "%.2f".format(item.price)
        val total = "%.2f".format(item.total())

        itemViewHolder.textViewAmount.text = item.amount.toString()
        itemViewHolder.textViewName.text = item.name
        itemViewHolder.textViewPrice.text = "S/.$price"
        itemViewHolder.textViewTotal.text = "S/.$total"
    }

    fun clear() {
        data = emptyList()
        notifyDataSetChanged()
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewAmount: TextView = view.findViewById(R.id.textViewAmount)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewPrice: TextView = view.findViewById(R.id.textViewPrice)
        val textViewTotal: TextView = view.findViewById(R.id.textViewTotal)

        fun bind() {}
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTotal: TextView = view.findViewById(R.id.textViewTotal)
        fun bind() {}
    }
}