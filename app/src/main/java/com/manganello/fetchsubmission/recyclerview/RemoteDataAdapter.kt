package com.manganello.fetchsubmission.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.manganello.fetchsubmission.R
import com.manganello.fetchsubmission.entity.RemoteData

class RemoteDataAdapter(private var dataMap: Map<Int, List<RemoteData>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Any>()

    init {
        setData(dataMap)
    }

    private fun setData(dataMap: Map<Int, List<RemoteData>>) {
        items.clear()
        dataMap.forEach { (listId, dataList) ->
            items.add(listId)
            items.addAll(dataList)
        }
        notifyDataSetChanged()
    }

    fun filterData(selectedListId: Int?) {
        items.clear()

        if (selectedListId == null) {
            dataMap.forEach { (listId, dataList) ->
                items.add(listId)
                items.addAll(dataList)
            }
        } else {
            dataMap[selectedListId]?.let { dataList ->
                items.addAll(dataList)
            }
        }

        notifyDataSetChanged()
    }



    override fun getItemViewType(position: Int): Int {
        return if (items[position] is Int) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.remote_data_item, parent, false)
            ItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bind(items[position] as Int)
        } else if (holder is ItemViewHolder) {
            holder.bind(items[position] as RemoteData)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val listIdTextView: TextView = itemView.findViewById(R.id.headerTextView)
        private val cardView: CardView = itemView.findViewById(R.id.header_cardview)
        fun bind(listId: Int) {
            val headingText = "List ID: $listId"
            listIdTextView.text = headingText
            val colors = listOf(
                R.color.colorGroup1,
                R.color.colorGroup2,
                R.color.colorGroup3
            )

            val colorIndex = listId % colors.size
            val color = itemView.context.getColor(colors[colorIndex])

            cardView.setCardBackgroundColor(color)
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val listIdTextView: TextView = itemView.findViewById(R.id.textViewListId)
        private val idTextView: TextView = itemView.findViewById(R.id.textViewId)
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        private val cardViewOne: CardView = itemView.findViewById(R.id.cardViewOne)
        private val cardViewTwo: CardView = itemView.findViewById(R.id.cardViewTwo)
        private val cardViewThree: CardView = itemView.findViewById(R.id.cardViewThree)
        private val cardViews = listOf(cardViewOne, cardViewTwo, cardViewThree)

        fun bind(RemoteData: RemoteData) {
            listIdTextView.text = RemoteData.listId.toString()
            idTextView.text = RemoteData.id.toString()
            nameTextView.text = RemoteData.name

            val colors = listOf(
                R.color.colorGroup1,
                R.color.colorGroup2,
                R.color.colorGroup3
            )

            val colorIndex = RemoteData.listId % colors.size
            val color = itemView.context.getColor(colors[colorIndex])

            cardViews.forEach {
                it.setCardBackgroundColor(color)
            }

        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }
}
