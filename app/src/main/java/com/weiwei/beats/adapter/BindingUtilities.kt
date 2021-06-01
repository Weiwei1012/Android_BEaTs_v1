package com.weiwei.beats.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.weiwei.beats.R
import com.weiwei.beats.database.Restaurant
import com.weiwei.beats.weather.WeatherViewModel

//for resolve app:setImage in the item_layout.xml
@BindingAdapter("setImage")
fun ImageView.setSceneImage(scene: Restaurant?) {
    scene?.let {
        if (scene.photoFile.isNotEmpty()) {
            Glide.with(this.context)
                .load(Uri.parse(scene.photoFile))
                .apply(RequestOptions().centerCrop())
                .into(this)
        } else {
            setImageResource(scene.photoId)
        }
    }
}
@BindingAdapter("setWeatherImage")
fun ImageView.setIconImage(icon: String?) {  //initially, livedata is null
    val accessURL = "${WeatherViewModel.ICON_URL}${icon}@2x.png"
    val iconUri = accessURL.toUri().buildUpon().scheme("https").build()

    if (icon == null) {
        setImageDrawable(null)
    }
    else {
        //download the weather icon from the website
        Glide.with(this)
            .load(iconUri)
            .apply(
                RequestOptions().placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}

//for stopping the progressbar
@BindingAdapter("Data")
fun ProgressBar.setViewVisibility(dataChecked: Any?) {
    visibility = if (dataChecked != null) View.GONE else View.VISIBLE
}