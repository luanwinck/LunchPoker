package br.com.cwi.lunchpoker.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.cwi.lunchpoker.R
import kotlinx.android.synthetic.main.room_row.view.*

class RomsAdapter: RecyclerView.Adapter<CustomVH>() {

    val rooms = listOf<String>("First", "Second", "3rd", "asdasd", "LLLALALALALAl")

    // 3 - Number os items
    override fun getItemCount(): Int {
        return rooms.size
    }

    // 5 - implement onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVH {
        // A view que sera criada para usar na lista, como cria-la?

        // 7 - LayoutInflator para conseguir a view pelo xml
        // O contexto da view sera o do parente.. viewGroup
        val layoutInflater = LayoutInflater.from(parent.context)

        // 8 - inflate da view xml
        val cellView = layoutInflater.inflate(R.layout.room_row, parent, false)

        // 9 - return a view  do tipo ViewHolder
        return CustomVH(cellView)

    }

    // 6 - implement onBindViewHolder
    override fun onBindViewHolder(holder: CustomVH, position: Int) {
        val room = rooms.get(position)
        holder.itemView.textView_id.text = room
        holder.itemView.textView_timestamp.text = "Aaaaaaaaa"
    }

}


// 4 - View Holder, extende RecyvlerView.ViewHolder recebe uma view e deve chamar o contrutor da classe pai com essa view
class CustomVH(view: View): RecyclerView.ViewHolder(view){

}