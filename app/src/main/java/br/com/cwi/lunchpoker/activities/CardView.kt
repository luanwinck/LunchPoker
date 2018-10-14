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
import br.com.cwi.lunchpoker.loadImage
import br.com.cwi.lunchpoker.services.api.models.RestaurantModel
import kotlinx.android.synthetic.main.view_card.*
import kotlinx.android.synthetic.main.view_card.view.*
import io.grpc.Deadline.after
import android.animation.AnimatorSet
import android.os.Handler


class CardView : DialogFragment() {

    lateinit var restaurant: RestaurantModel

    private var animationDone = false

    private val ANIMATION_DURATION = 2000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.view_card, container, false)

        restaurant.let {
//            view.textCardRestaurant.text = it.name
//            view.imageCardRestaurant.loadImage(it.image)
            view.setOnClickListener {card ->
                this.transform(card)
            }
        }

        return view
    }

    private fun rotate(card: View) {
        if (!animationDone) {
            val rotate = ObjectAnimator.ofFloat(card, View.ROTATION_Y, 0f, 180f)
            rotate.setDuration(2500)
            rotate.interpolator = AccelerateInterpolator()
            rotate.start()
            animationDone = true
        }
    }

    private fun transform(card: View) {
        if (!animationDone) {
            val fadeOut = ObjectAnimator.ofFloat(card, "alpha", 1f, 0f)
            fadeOut.duration = ANIMATION_DURATION.toLong()

            Handler().postDelayed({
                textCardRestaurant.text = restaurant.name
                imageCardRestaurant.loadImage(restaurant.image)
            }, ANIMATION_DURATION.toLong())

            val fadeIn = ObjectAnimator.ofFloat(card, "alpha", 0f, 1f)
            fadeIn.duration = ANIMATION_DURATION.toLong()
            val mAnimationSet = AnimatorSet()

            mAnimationSet.play(fadeIn).after(fadeOut)
            mAnimationSet.start()

            animationDone = true
        }
    }
}