package br.com.cwi.lunchpoker

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.com.cwi.lunchpoker.adapters.EdgeDecorator
import br.com.cwi.lunchpoker.adapters.VoteAdapter
import br.com.cwi.lunchpoker.models.Location
import br.com.cwi.lunchpoker.services.ApiService
import com.onesignal.OSNotification
import com.onesignal.OneSignal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_in_room.*
import org.json.JSONObject

class InRoomActivity : AppCompatActivity() {

    var locations: MutableList<Location> = mutableListOf()

    private var dialog: ProgressDialog? = null

    private val retrofitServe by lazy {
        ApiService().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_room)

        var ac = supportActionBar
        ac?.title = "Carregando..."
        ac?.setDisplayHomeAsUpEnabled(true)

        if(Session.isHost){
            fabCloseVotes.visibility = View.VISIBLE
            fabCloseVotes.setOnClickListener {
                closeVotes()
            }
        }

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationReceivedHandler(object : OneSignal.NotificationReceivedHandler {
                    override fun notificationReceived(notification: OSNotification) {
                        val data = notification.payload.additionalData

                        var local = data.getString("local")

                        val dialogBuilder = AlertDialog.Builder(this@InRoomActivity)
                        val inflater = layoutInflater
                        val dialogView = inflater.inflate(R.layout.view_done, null)
                        val dialog = dialogBuilder.create()

                        var btnOK = dialogView.findViewById<Button>(R.id.btnOK)
                        var txtLocal = dialogView.findViewById<TextView>(R.id.txtLocal)

                        txtLocal.text = Session.getLocal(local)

                        btnOK.setOnClickListener {
                            dialog.dismiss()
                            finish()
                        }

                        dialog.setCancelable(false)

                        dialog.setView(dialogView)
                        dialog.show()

                        Session.isHost = false

                        var tags = mutableListOf<String>()
                        tags.add("sala")
                        OneSignal.deleteTags(tags)
                    }
                })
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()
    }

    override fun onBackPressed() {
        closeVotes()
        super.onBackPressed()
    }

    override fun onResume() {
        if(Session.roomId == ""){
            dialog = ProgressDialog.show(this, "",
                    "Criando sala...", true)
            dialog!!.setCancelable(false)
            setRoom()
        }else{
            dialog = ProgressDialog.show(this, "",
                    "Carregando sala...", true)
            dialog!!.setCancelable(false)
            getRoom()
        }
        super.onResume()
    }

    fun inflateList(){
        rcvLocais.adapter = VoteAdapter(locations, this, ::setVote)
        rcvLocais.addItemDecoration(EdgeDecorator(16))
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcvLocais.layoutManager = layoutManager
        dialog!!.dismiss()
    }

    fun setTag(){
        val tags = JSONObject()
        tags.put("sala", Session.roomId)
        OneSignal.sendTags(tags)
    }

    fun setVote(local: Int){
        retrofitServe.setVoto(Session.roomId, local)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    result.let {
                        if(it.status){
                            Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this,"Erro: "+it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }, {
                    it.message?.let {
                        Toast.makeText(this,"Erro: "+it, Toast.LENGTH_SHORT).show()
                    } ?: run {
                        Toast.makeText(this,"Um erro inesperado aconteceu!", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    fun closeVotes(){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.view_close_votes, null)
        val dialog = dialogBuilder.create()

        var btnEncerrar = dialogView.findViewById<Button>(R.id.btnEncerrar)
        var btnCancelar = dialogView.findViewById<Button>(R.id.btnCancelar)

        btnEncerrar.setOnClickListener {
            dialog.dismiss()
            getVote()
        }

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setView(dialogView)
        dialog.show()
    }

    fun getVote(){
        retrofitServe.getVoto(Session.roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    result.let {
                        if(it.status){
                            //código
                        }else{
                            Toast.makeText(this,"Erro: "+it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }, {
                    it.message?.let {
                        Toast.makeText(this,"Erro: "+it, Toast.LENGTH_SHORT).show()
                    } ?: run {
                        Toast.makeText(this,"Um erro inesperado aconteceu!", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    fun setRoom(){
        retrofitServe.setSalas(Session.getLocales())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    result.let {
                        if(it.status){
                            var ac = supportActionBar
                            ac?.title = "Sala "+it.data!!.id
                            Session.locales.clear()
                            Session.roomId = it.data!!.id.toString()
                            locations = it.data!!.locais as MutableList<Location>
                            Session.trueLocales.clear()
                            Session.trueLocales = locations
                            setTag()
                            inflateList()
                        }else{
                            Toast.makeText(this,"Erro: "+it.message, Toast.LENGTH_SHORT).show()
                            dialog!!.dismiss()
                            finish()
                        }
                    }
                }, {
                    it.message?.let {
                        Toast.makeText(this,"Erro: "+it, Toast.LENGTH_SHORT).show()
                    } ?: run {
                        Toast.makeText(this,"Um erro inesperado aconteceu!", Toast.LENGTH_SHORT).show()
                    }
                    dialog!!.dismiss()
                    finish()
                })
    }

    fun getRoom(){
        retrofitServe.getSalas(Session.roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    result.let {
                        if(it.status){
                            var ac = supportActionBar
                            ac?.title = "Sala "+it.data!!.id
                            locations = it.data!!.locais as MutableList<Location>
                            Session.trueLocales.clear()
                            Session.trueLocales = locations
                            setTag()
                            inflateList()
                        }else{
                            Toast.makeText(this,"Erro: "+it.message, Toast.LENGTH_SHORT).show()
                            dialog!!.dismiss()
                            finish()
                        }
                    }
                }, {
                    it.message?.let {
                        Toast.makeText(this,"Erro: "+it, Toast.LENGTH_SHORT).show()
                    } ?: run {
                        Toast.makeText(this,"Um erro inesperado aconteceu!", Toast.LENGTH_SHORT).show()
                    }
                    dialog!!.dismiss()
                    finish()
                })
    }

    //definições do botão back na action bar
    override fun onSupportNavigateUp(): Boolean {
        closeVotes()
        return super.onSupportNavigateUp()
    }
}
