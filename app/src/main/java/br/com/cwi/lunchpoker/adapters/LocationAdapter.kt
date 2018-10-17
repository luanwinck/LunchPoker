package br.com.cwi.lunchpoker.adapters

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cwi.lunchpoker.LocationType
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.Session
import br.com.cwi.lunchpoker.models.Location
import kotlinx.android.synthetic.main.view_location.view.*

class LocationAdapter (private val locations: List<Location>,
                       private val context: Context) : RecyclerView.Adapter<HolderLocation>() {

    override fun onBindViewHolder(holder: HolderLocation, position: Int) {
        val location = locations[position]
        holder.bindView(location, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderLocation {
        val view = LayoutInflater.from(context).inflate(R.layout.view_location, parent, false)
        return HolderLocation(view)
    }

    override fun getItemCount(): Int {
        return locations.size
    }
}

class HolderLocation(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(location: Location, position: Int) {

        itemView.txtNome.text = location.nome

        when (location.tipo){
            LocationType.RESTAURANTE.toString() -> {
                itemView.imgType.setImageResource(R.drawable.ic_restaurante)
            }
            LocationType.HAMBURGUERIA.toString() -> {
                itemView.imgType.setImageResource(R.drawable.ic_hamburguer)
            }
            LocationType.PIZZARIA.toString() -> {
                itemView.imgType.setImageResource(R.drawable.ic_pizzaria)
            }
            LocationType.CHURRASCARIA.toString() -> {
                itemView.imgType.setImageResource(R.drawable.ic_churrascaria)
            }
            LocationType.ORIENTAL.toString() -> {
                itemView.imgType.setImageResource(R.drawable.ic_asiatica)
            }
            else -> {
                itemView.imgType.setImageResource(R.drawable.ic_outros)
            }
        }

        if(Session.locales.indexOf(location.id.toString()) > -1){
            itemView.imgPlus.setImageResource(R.drawable.ic_remove)

            val matrix = ColorMatrix()
            matrix.setSaturation(0f)
            val filter = ColorMatrixColorFilter(matrix)

            itemView.imgPlus.setColorFilter(filter)
            itemView.imgPlus.alpha = 0.20f
        }else{
            itemView.imgPlus.setImageResource(R.drawable.ic_plus)

            val matrix = ColorMatrix()
            matrix.setSaturation(1f)
            val filter = ColorMatrixColorFilter(matrix)

            itemView.imgPlus.setColorFilter(filter)
            itemView.imgPlus.alpha = 1f
        }

        itemView.imgPlus.setOnClickListener {

            if(Session.locales.indexOf(location.id.toString()) > -1){
                Session.locales.remove(location.id.toString())

                itemView.imgPlus.setImageResource(R.drawable.ic_plus)

                val matrix = ColorMatrix()
                matrix.setSaturation(1f)
                val filter = ColorMatrixColorFilter(matrix)

                itemView.imgPlus.setColorFilter(filter)
                itemView.imgPlus.alpha = 1f
            }else{
                Session.locales.add(location.id.toString())

                itemView.imgPlus.setImageResource(R.drawable.ic_remove)

                val matrix = ColorMatrix()
                matrix.setSaturation(0f)
                val filter = ColorMatrixColorFilter(matrix)

                itemView.imgPlus.setColorFilter(filter)
                itemView.imgPlus.alpha = 0.20f
            }

        }

    }
}