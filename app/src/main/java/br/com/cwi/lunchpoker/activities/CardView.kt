package br.com.cwi.lunchpoker.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.services.api.models.RestaurantModel
import kotlinx.android.synthetic.main.view_card.view.*

class CardView : DialogFragment() {

    lateinit var restaurant: RestaurantModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.view_card, container, false)

        restaurant.let {
            view.textCardRestaurant.text = it.name
            view.setOnClickListener {card ->
                this.rotate(card)
            }
        }

        return view
    }

    private fun rotate(card: View) {
        val rotate = ObjectAnimator.ofFloat(card, View.ROTATION_Y, 180f, 0f)
        rotate.setDuration(2500)
        rotate.interpolator = AccelerateInterpolator()
        rotate.start()
    }

}