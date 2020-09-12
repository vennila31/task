package com.example.systemtaskvennilag

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.systemtaskvennilag.model.Data
import kotlinx.android.synthetic.main.item_view.view.*

class MyAdapter (private var dataList: MutableList<Data>) : RecyclerView.Adapter<MyViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false))
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]

        val name = holder.itemView.name
        val price = holder.itemView.price
        val change = holder.itemView.change
        val img = holder.itemView.img

        val num = data.priceUsd.toDouble()
        val priceValue = "%.4f".format(num)

        val nums = data.changePercent24Hr.toDouble()
        val changes = "%.4f".format(nums)


        name.text = data.name
        price.text = "$$priceValue"
        change.text = "$changes%"
        img.text = data.symbol
    }

    fun updateList(list: MutableList<Data>){
        dataList = list
        notifyDataSetChanged()
    }
}