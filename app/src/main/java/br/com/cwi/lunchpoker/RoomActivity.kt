package br.com.cwi.lunchpoker

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.cwi.lunchpoker.adapters.EdgeDecorator
import br.com.cwi.lunchpoker.adapters.LocationAdapter
import br.com.cwi.lunchpoker.models.Location
import br.com.cwi.lunchpoker.services.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_location.*
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity : AppCompatActivity() {

    var locations: MutableList<Location> = mutableListOf()

    private var dialog: ProgressDialog? = null

    private val retrofitServe by lazy {
        ApiService().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        var ac = supportActionBar
        ac?.title = getString(R.string.new_room)
        ac?.setDisplayHomeAsUpEnabled(true)

        fabAddLocation.setOnClickListener {
            startActivity(Intent(this, NewLocationActivity::class.java))
        }

    }

    override fun onResume() {

        dialog = ProgressDialog.show(this, "",
                "Carregando locais...", true)
        dialog!!.setCancelable(false)
        getLocations()

        super.onResume()
    }

    fun getLocations(){
        retrofitServe.getLocais()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    result.let {
                        if(it.status){
                            locations = it.data
                            inflateList()
                        }else{
                            Toast.makeText(this,"Erro: "+it.message, Toast.LENGTH_SHORT).show()
                            dialog!!.dismiss()
                        }
                    }
                }, {
                    it.message?.let {
                        Toast.makeText(this,"Erro: "+it, Toast.LENGTH_SHORT).show()
                    } ?: run {
                        Toast.makeText(this,"Um erro inesperado aconteceu!", Toast.LENGTH_SHORT).show()
                    }
                    dialog!!.dismiss()
                })
    }

    fun inflateList(){
        rcvLocais.adapter = LocationAdapter(locations, this)
        rcvLocais.addItemDecoration(EdgeDecorator(16))
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcvLocais.layoutManager = layoutManager
        dialog!!.dismiss()
    }

    //inflando o menu na action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new_location, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //onClicks dos itens de menu da action bar
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.btnSalvar){
            Session.isHost = true
            startActivity(Intent(this, InRoomActivity::class.java))
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    //definições do botão back na action bar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
