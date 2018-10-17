package br.com.cwi.lunchpoker.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.Session
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        btnEntrar.setOnClickListener {
            enterCode()
        }

        btnNovaSala.setOnClickListener {
            startActivity(Intent(this, RoomActivity::class.java))
        }

    }

    override fun onResume() {
        Session.locales.clear()
        Session.trueLocales.clear()
        Session.roomId = ""
        super.onResume()
    }

    fun enterCode(){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.view_room_code, null)
        val dialog = dialogBuilder.create()

        var btnEntrar = dialogView.findViewById<Button>(R.id.btnEntrar)
        var txtCode = dialogView.findViewById<EditText>(R.id.txtCode)

        btnEntrar.setOnClickListener {
            if(txtCode.text.toString() != ""){
                Session.roomId = txtCode.text.toString()
                startActivity(Intent(this, InRoomActivity::class.java))
                dialog.dismiss()
            }else{
                txtCode.requestFocus()
                Toast.makeText(this, "Informe o número da sala!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.setView(dialogView)
        dialog.show()
    }

    //inflando o menu na action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_launch, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //onClicks dos itens de menu da action bar
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.btnQRCode){
            startActivity(Intent(this, ScanningActivity::class.java))
        }else if(item?.itemId == R.id.btnSobre){
            startActivity(Intent(this, AboutActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }
}
