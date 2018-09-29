package br.com.cwi.lunchpoker.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.services.api.models.RestaurantModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query


class RestaurantAdapter(private val listener: OnRestaurantSelectedListener?, query: Query): FirestoreAdapter<RestaurantAdapter.ViewHolder>(query) {

    interface OnRestaurantSelectedListener {
        fun OnRestaurantSelectedListener(restaurant: DocumentSnapshot)
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

        fun bind(snapshot: DocumentSnapshot,
                 listener: OnRestaurantSelectedListener?) {

            val restaurant = snapshot.toObject(RestaurantModel::class.java)
//            val resources = view.resources

//            Glide.with(imageView.getContext())
//                    .load(restaurant!!.getPhoto())
//                    .into(imageView)

//            nameRestaurant(restaurant!!.getName())

            nameRestaurant = view.findViewById(R.id.nameRestaurant)

            nameRestaurant.text = restaurant?.name


            // Click listener
//            itemView.setOnClickListener {
//                if (listener != null) {
//                    listener!!.onRestaurantSelected(snapshot)
//                }
//            }
        }
    }
}

