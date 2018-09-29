package br.com.cwi.lunchpoker.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.adapters.RestaurantAdapter
import br.com.cwi.lunchpoker.services.api.models.RestaurantModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_restaurant.*

class MainActivity : AppCompatActivity(), RestaurantAdapter.OnRestaurantSelectedListener {

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun OnRestaurantSelectedListener(restaurant: DocumentSnapshot) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
