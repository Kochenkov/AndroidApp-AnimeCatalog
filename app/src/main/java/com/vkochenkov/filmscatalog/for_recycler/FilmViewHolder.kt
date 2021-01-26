package com.vkochenkov.filmscatalog.for_recycler

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.Film

class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val filmTitle: TextView = itemView.findViewById(R.id.item_film_title)
    val filmImage: ImageView = itemView.findViewById(R.id.item_film_image)
    val filmDetailsBtn: Button = itemView.findViewById(R.id.item_film_details_btn)

    fun bind(item: Film) {
        filmTitle.text = itemView.context.getString(item.titleRes)
        filmImage.setImageResource(item.imageRes)
    }
}