package br.com.cwi.lunchpoker

import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso

fun AppCompatActivity.longToast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun AppCompatActivity.shortToast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(url: String?) {
    url?.let {
        Picasso.with(context).load(it).into(this)
    }
}