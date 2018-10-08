package br.com.cwi.lunchpoker.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.services.api.models.RestaurantModel
import kotlinx.android.synthetic.main.view_card.view.*

class CardView : DialogFragment() {

    lateinit var restaurant: RestaurantModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.view_card, container, false)

        restaurant.run {
            view.textCardRestaurant.text = name
        }

        return view
    }

}