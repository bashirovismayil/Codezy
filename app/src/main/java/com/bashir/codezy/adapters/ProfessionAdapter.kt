package com.bashir.codezy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bashir.codezy.R

class ProfessionAdapter(
    private val professions: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ProfessionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_profession, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profession = professions[position]
        holder.bind(profession)
    }

    override fun getItemCount() = professions.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(profession: String) {
            itemView.setOnClickListener { onItemClick(profession) }
            itemView.findViewById<TextView>(R.id.textViewProfession).text = profession
        }
    }
}
