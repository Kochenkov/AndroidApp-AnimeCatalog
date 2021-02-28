package com.vkochenkov.filmscatalog.presentation.view.recycler

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.data.Film

class FavouriteFilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val filmTitle: TextView = itemView.findViewById(R.id.item_favourite_film_title)
    val filmImage: ImageView = itemView.findViewById(R.id.item_favourite_film_image)
    val filmDetailsBtn: Button = itemView.findViewById(R.id.item_favourite_film_details_btn)
    val filmDeleteBtn: ImageView = itemView.findViewById(R.id.item_favourite_film_delete_btn)

    fun bind(item: Film) {
        filmTitle.text = item.title
       // filmImage.setImageResource(item.imageRes)

        if (item.selected) {
            filmTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
        } else {
            filmTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorBlack))
        }
    }
}