package br.com.cwi.lunchpoker.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.loadImage
import br.com.cwi.lunchpoker.services.api.models.RestaurantModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query


class RestaurantAdapter(private val listener: OnRestaurantSelectedListener, query: Query): FirestoreAdapter<RestaurantAdapter.ViewHolder>(query) {

    interface OnRestaurantSelectedListener {
        fun onRestaurantSelected(restaurant: RestaurantModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_restaurant, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position), listener)
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        lateinit var nameRestaurant: TextView
        lateinit var imageRestaurant: ImageView
        lateinit var descriptionRestaurant: TextView

        fun bind(snapshot: DocumentSnapshot,
                 listener: OnRestaurantSelectedListener) {

             val restaurant = snapshot.toObject(RestaurantModel::class.java)

            nameRestaurant = view.findViewById(R.id.nameRestaurant)
            imageRestaurant = view.findViewById(R.id.imageRestaurant)
            descriptionRestaurant = view.findViewById(R.id.descriptionRestaurant)



            restaurant?.let {restautant ->
                nameRestaurant.text = restaurant.name
                imageRestaurant.loadImage(restaurant.image)
                descriptionRestaurant.text = restaurant.description


                itemView.setOnClickListener {
                    listener.onRestaurantSelected(restaurant)
                }

            }

        }
    }
}

