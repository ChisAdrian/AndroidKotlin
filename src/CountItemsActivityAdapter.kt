package com.example.CalculatorInventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountItemsActivityAdapter(private val itemList: List<ModelCount>,
                                private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<CountItemsActivityAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val mid: TextView = itemView.findViewById(R.id.tvId)
        val texttvItem: TextView = itemView.findViewById(R.id.tvItem)
        val texttvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val texttvQty: TextView = itemView.findViewById(R.id.tvQty)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]
                    onItemClick(  item.V_texttvId + "," + item.V_texttvItem+ "," + item.V_texttvDescription+ "," + item.V_texttvQty  )
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.count_items_activity_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.mid.text = currentItem.V_texttvId
        holder.texttvItem.text = currentItem.V_texttvItem
        holder.texttvDescription.text = currentItem.V_texttvDescription
        holder.texttvQty.text = currentItem.V_texttvQty.toString()

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

data class ModelCount(
    var V_texttvId: String,
    var V_texttvItem: String,
    var V_texttvDescription: String,
    var V_texttvQty: Double
)