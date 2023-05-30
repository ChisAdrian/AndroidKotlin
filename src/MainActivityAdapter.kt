package com.example.CalculatorInventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainActivityAdapter(
    private val listViewModel_count: List<ModelMain>,
    private val listener: OnItemClickListener) :

    RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_activity_row, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inModel = listViewModel_count[position]
        holder.txt_tv_row_main.text = inModel.Name
        holder.txt_tv_tobe_counted.text = inModel.tobe_counted
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return listViewModel_count.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView),
        View.OnClickListener
    {
        val txt_tv_row_main: TextView = itemView.findViewById(R.id.tv_row_main)
        val txt_tv_tobe_counted: TextView = itemView.findViewById(R.id.tv_tobe_counted)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}

data class ModelMain(var Name: String, val tobe_counted: String)