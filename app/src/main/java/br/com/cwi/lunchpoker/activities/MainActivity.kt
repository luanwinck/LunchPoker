package br.com.cwi.lunchpoker.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.adapters.RestaurantAdapter
import com.google.firebase.firestore.DocumentSnapshot

class MainActivity : AppCompatActivity(), RestaurantAdapter.OnRestaurantSelectedListener {

    override fun OnRestaurantSelectedListener(restaurant: DocumentSnapshot) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
