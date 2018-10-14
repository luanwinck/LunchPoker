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
import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup


class MainActivity : AppCompatActivity(), RestaurantAdapter.OnRestaurantSelectedListener {

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onRestaurantSelected(restaurant: RestaurantModel) {
//        var dialog = CardView()


        val dialoglayout = layoutInflater.inflate(R.layout.view_card, null)
        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)

        dialog.setContentView(dialoglayout)
        // dialog.isCancelable = true
        dialog.setCanceledOnTouchOutside(true)
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.show()


        val rotate = ObjectAnimator.ofFloat(dialoglayout, View.ROTATION_Y, 180f, 0f)
        rotate.setDuration(2500)
        rotate.interpolator = AccelerateInterpolator()
        rotate.start()

        //dialog.restaurant = restaurant
        //dialog.show(supportFragmentManager, "CardView")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restaurants =  firebaseFirestore.collection("restaurantes").orderBy("name")

        val adapter = RestaurantAdapter(this, restaurants)

        restaurantRecyclerView.adapter = adapter
        restaurantRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter.startListening()

    }
}
