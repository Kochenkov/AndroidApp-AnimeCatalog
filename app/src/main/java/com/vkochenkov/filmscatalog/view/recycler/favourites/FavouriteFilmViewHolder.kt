package com.vkochenkov.filmscatalog.view.recycler.favourites

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.LocalDataStore
import com.vkochenkov.filmscatalog.model.db.Film

class FavouriteFilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val filmTitle: TextView = itemView.findViewById(R.id.item_favourite_film_title)
    val filmImage: ImageView = itemView.findViewById(R.id.item_favourite_film_image)
    val filmDetailsBtn: Button = itemView.findViewById(R.id.item_favourite_film_details_btn)
    val filmDeleteBtn: ImageView = itemView.findViewById(R.id.item_favourite_film_delete_btn)

    fun bind(item: Film) {
        filmTitle.text = item.title

        Glide.with(itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.im_default_film)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(filmImage)

        if (item.serverName.equals(LocalDataStore.currentSelectedFilm?.serverName)) {
            filmTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
        } else {
            filmTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorBlack))
        }
    }
}