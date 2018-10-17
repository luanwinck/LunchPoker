package br.com.cwi.lunchpoker.activities

import android.app.ProgressDialog
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import br.com.cwi.lunchpoker.enums.LocationType
import br.com.cwi.lunchpoker.R
import br.com.cwi.lunchpoker.services.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_location.*

class NewLocationActivity : AppCompatActivity() {

    private val retrofitServe by lazy {
        ApiService().create()
    }

    private var locationType: String = LocationType.RESTAURANTE.toString()

    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_location)

        var ac = supportActionBar
        ac?.title = getString(R.string.new_location)
        ac?.setDisplayHomeAsUpEnabled(true)

        clearSelection()

        selectType(imgRestaurante)

        imgRestaurante.setOnClickListener {
            clearSelection()
            selectType(imgRestaurante)
            locationType = LocationType.RESTAURANTE.toString()
        }

        imgHamburgueria.setOnClickListener {
            clearSelection()
            selectType(imgHamburgueria)
            locationType = LocationType.HAMBURGUERIA.toString()
        }

        imgPizzaria.setOnClickListener {
            clearSelection()
            selectType(imgPizzaria)
            locationType = LocationType.PIZZARIA.toString()
        }

        imgChurrascaria.setOnClickListener {
            clearSelection()
            selectType(imgChurrascaria)
            locationType = LocationType.CHURRASCARIA.toString()
        }

        imgOriental.setOnClickListener {
            clearSelection()
            selectType(imgOriental)
            locationType = LocationType.ORIENTAL.toString()
        }

        imgOutro.setOnClickListener {
            clearSelection()
            selectType(imgOutro)
            locationType = LocationType.OUTRO.toString()
        }

    }

    fun setLocal(){
        retrofitServe.setLocais(txtLocalName.text.toString(), locationType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    result.let {
                        if(it.status){
                            Toast.makeText(this,txtLocalName.text.toString()+" salvo com sucesso!",Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this,"Erro: "+it.message,Toast.LENGTH_SHORT).show()
                        }
                        dialog!!.dismiss()
                    }
                }, {
                    it.message?.let {
                        Toast.makeText(this,"Erro: "+it,Toast.LENGTH_SHORT).show()
                    } ?: run {
                        Toast.makeText(this,"Um erro inesperado aconteceu!",Toast.LENGTH_SHORT).show()
                    }
                    dialog!!.dismiss()
        })
    }

    //inflando o menu na action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new_location, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //onClicks dos itens de menu da action bar
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.btnSalvar){
            if(txtLocalName.text.toString() != "") {
                dialog = ProgressDialog.show(this, "",
                        "Salvando local...", true)
                dialog!!.setCancelable(false)
                setLocal()
            }else{
                Toast.makeText(this,"Informe o nome do local!",Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun selectType(imageView: ImageView){
        val matrix = ColorMatrix()
        matrix.setSaturation(1f)
        val filter = ColorMatrixColorFilter(matrix)

        imageView.setColorFilter(filter)
        imageView.alpha = 1f
    }

    fun clearSelection(){
        val matrix = ColorMatrix()
        matrix.setSaturation(0f)
        val filter = ColorMatrixColorFilter(matrix)

        imgRestaurante.setColorFilter(filter)
        imgRestaurante.alpha = 0.20f

        imgHamburgueria.setColorFilter(filter)
        imgHamburgueria.alpha = 0.20f

        imgPizzaria.setColorFilter(filter)
        imgPizzaria.alpha = 0.20f

        imgChurrascaria.setColorFilter(filter)
        imgChurrascaria.alpha = 0.20f

        imgOriental.setColorFilter(filter)
        imgOriental.alpha = 0.20f

        imgOutro.setColorFilter(filter)
        imgOutro.alpha = 0.20f
    }

    //definições do botão back na action bar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
