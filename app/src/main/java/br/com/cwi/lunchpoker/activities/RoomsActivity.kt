package br.com.cwi.lunchpoker.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.adapters.RomsAdapter
import kotlinx.android.synthetic.main.activity_rooms.*

class RoomsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        // 1 - Set linearLeyaoutManager as reciclerView LayoutManager
        reciclerView_rooms.layoutManager = LinearLayoutManager(this)

        // 2 - Adapter, think as a dataSource
        reciclerView_rooms.adapter = RomsAdapter()

    }
}
