package com.example.projekt

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.*

class RecyclerAdapter(var ct: Context, var titleString: Array<String>,var images: Array<Int>): RecyclerView.Adapter<RecyclerAdapter.OurViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row,parent, false)

        Log.d("Recycleradapterlog", "OnCreateViewHolder lefutott")

        return OurViewHolder(view)
    }

    override fun onBindViewHolder(holder: OurViewHolder, position: Int) {
        Log.d("onBindViewHolder", titleString[position])
        holder.textViewTitle.setText(titleString[position])
        holder.imageViewSport.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
       return titleString.size
    }

    inner class OurViewHolder(itemView: View, ) : RecyclerView.ViewHolder(itemView){
        var textViewTitle: TextView
        var imageViewSport: ImageView

        init{
            textViewTitle = itemView.findViewById(R.id.sport_text)
            imageViewSport = itemView.findViewById(R.id.sport_image)
        }
    }


}