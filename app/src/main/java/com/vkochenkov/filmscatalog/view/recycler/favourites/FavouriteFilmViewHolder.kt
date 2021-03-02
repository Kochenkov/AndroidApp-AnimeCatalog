package com.vkochenkov.filmscatalog.view.recycler.favourites

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.vkochenkov.filmscatalog.R
import com.vkochenkov.filmscatalog.model.db.Film

class FavouriteFilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val filmTitle: TextView = itemView.findViewById(R.id.item_favourite_film_title)
    val filmImage: ImageView = itemView.findViewById(R.id.item_favourite_film_image)
    val filmDetailsBtn: Button = itemView.findViewById(R.id.item_favourite_film_details_btn)
    val filmDeleteBtn: ImageView = itemView.findViewById(R.id.item_favourite_film_delete_btn)

    fun bind(item: Film) {
        filmTitle.text = item.title

        //загрузка картинки по http
        Glide.with(itemView.context)
            .asBitmap()
            .load(item.imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    filmImage.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // this is called when imageView is cleared on lifecycle call or for
                    // some other reason.
                    // if you are referencing the bitmap somewhere else too other than this imageView
                    // clear it here as you can no longer have the bitmap
                    //todo
                }
            })

//        if (item.selected) {
//            filmTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
//        } else {
//            filmTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorBlack))
//        }
    }
}