package com.laube.tech.countryexplorer.util

import android.content.Context
import android.net.Uri

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.laube.tech.countryexplorer.R
import java.net.URI


fun getProgressDrawable(context: Context) :CircularProgressDrawable{
    return CircularProgressDrawable(context).apply{
        strokeWidth = 5f
        centerRadius = 10f
        start()
    }
}

fun ImageView.loadImage(uri: String?, prgressDrawable: CircularProgressDrawable){
    val  options = RequestOptions()
        .placeholder(R.drawable.checkered)
        .error(R.drawable.checkered)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

fun ImageView.loadSVG(uriString: String?){
    val uri = Uri.parse(uriString)

    GlideToVectorYou
        .init()
        .setPlaceHolder(R.drawable.checkered,R.drawable.checkered)
        .with(this.context)
        .load(uri, this)
}

