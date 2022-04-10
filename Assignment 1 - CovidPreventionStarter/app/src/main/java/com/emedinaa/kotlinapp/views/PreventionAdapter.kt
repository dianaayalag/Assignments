package com.emedinaa.kotlinapp.views

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emedinaa.kotlinapp.Prevention
import com.emedinaa.kotlinapp.R

class PreventionAdapter(private var preventions: List<Prevention>,
private val itemAction:(item:Prevention)->Unit) :
    RecyclerView.Adapter<PreventionAdapter.PreventionViewHolder>() {

    fun update(items:List<Prevention>) {
        preventions = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreventionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_prevention,
            parent, false
        )
        return PreventionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreventionViewHolder, position: Int) {
        //val item = preventions[position]
        holder.bind(preventions[position])
    }

    override fun getItemCount(): Int = preventions.size

    inner class PreventionViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.imageView)
        private val textViewTitle = view.findViewById<TextView>(R.id.textViewTitle)
        private val textViewDesc = view.findViewById<TextView>(R.id.textViewDesc)

        fun bind(entity: Prevention) {
            /*textViewTitle.text = entity.title
            textViewDesc.text = entity.desc
            imageView.setImageResource(entity.image)*/
            with(entity) {
                textViewTitle.text = title
                textViewDesc.text = desc
                imageView.setImageResource(image)
            }

            view.setOnClickListener {
                Log.v("CONSOLE","entity $entity")
                itemAction(entity)
            }
        }
    }


}