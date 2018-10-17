package br.com.cwi.lunchpoker.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import br.com.cwi.lunchpoker.Session
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanningActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mScannerView = ZXingScannerView(this)
        setContentView(mScannerView)

        var ac = supportActionBar
        ac?.title = "Ler QR Code"
        ac?.setDisplayHomeAsUpEnabled(true)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),2)
        }else{

        }
    }

    override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }
    override fun handleResult(rawResult: Result) {
        Session.roomId = rawResult.text
        Session.isHost = false
        Session.trueLocales.clear()
        Session.locales.clear()

        onResponse()

        mScannerView!!.resumeCameraPreview(this)
    }

    fun onResponse(){
        startActivity(Intent(this, InRoomActivity::class.java))
        finish()
    }

    //definições do botão back na action bar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
