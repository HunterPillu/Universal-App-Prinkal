package com.prinkal.searchableapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prinkal.searchableapp.R
import com.prinkal.searchableapp.data.model.SampleData
import kotlinx.android.synthetic.main.pq_item_sample.view.*

class MainAdapter(
    private val users: List<SampleData>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(sample: SampleData) {
            itemView.tvTitle.text = sample.title
            itemView.tvDesc.text = sample.description
            Glide.with(itemView.ivImage.context)
                .load(sample.photoUrl)
                .into(itemView.ivImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pq_item_sample, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])


}