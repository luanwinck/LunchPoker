package br.com.cwi.lunchpoker.activities

import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.*
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.R.id.restaurantRecyclerView
import br.com.cwi.lunchpoker.adapters.RestaurantAdapter
import br.com.cwi.lunchpoker.services.api.models.RestaurantModel
import br.com.cwi.lunchpoker.shortToast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_card.*
import kotlinx.android.synthetic.main.view_restaurant.*

class MainActivity : AppCompatActivity(), RestaurantAdapter.OnRestaurantSelectedListener {

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private var card: RelativeLayout? = null

    override fun onRestaurantSelected(restaurant: RestaurantModel) {
        val dialog = CardView()
        dialog.restaurant = restaurant
        dialog.show(supportFragmentManager, "CardView")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restaurants =  firebaseFirestore.collection("restaurantes").orderBy("name")

        val adapter = RestaurantAdapter(this, restaurants)

        restaurantRecyclerView.adapter = adapter
        restaurantRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter.startListening()

        card = findViewById(R.id.cardView)

        card?.setOnClickListener {
            shortToast("CARD")

           this.rotate()
        }
    }

    private fun rotate() {
        val rotate = ObjectAnimator.ofFloat(card, View.ROTATION_Y, 180f, 0f)
        rotate.setDuration(2500)
        rotate.interpolator = AccelerateInterpolator()
        rotate.start()
    }
}
