package br.com.cwi.lunchpoker.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.cwi.lunchpoker.BuildConfig
import br.com.cwi.lunchpoker.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        var ac = supportActionBar
        ac?.title = "Sobre"
        ac?.setDisplayHomeAsUpEnabled(true)

        txtVersion.text = "Versão "+ BuildConfig.VERSION_NAME

    }

    //definições do botão back na action bar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
