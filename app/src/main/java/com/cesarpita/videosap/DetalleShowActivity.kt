package com.cesarpita.videosap

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.cesarpita.videosap.daos.AppDatabase
import com.cesarpita.videosap.modelos.Show
import com.cesarpita.videosap.modelos.ShowPreferido
import kotlinx.android.synthetic.main.activity_detalle_show.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleShowActivity : AppCompatActivity() {
    companion object{
        public  val SHOW = "SHOW"
    }
    //lateinit que se inicializa despues
    lateinit var showSeleccionado : Show
    lateinit var shared : SharedPreferences
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_show)
        db = AppDatabase.getDatabase(this)
        showSeleccionado = intent.getSerializableExtra(SHOW) as Show
        //Cambia el titulo de la actividad
        setTitle(showSeleccionado.name)

        Toast.makeText(this,showSeleccionado.name , Toast.LENGTH_SHORT).show()
        shared = getSharedPreferences(getString(R.string.favoritos),Context.MODE_PRIVATE)
        val demoCollectionAdapter = DemoPageAdapter(supportFragmentManager)
        paginador.adapter = demoCollectionAdapter
        tabs_layout.setupWithViewPager(paginador)

        favorito.setOnClickListener{
            //guardar en shared preferences
            GlobalScope.launch {
                guardarFavorito(showSeleccionado.id.toLong())
                cambiarIcono()
            }
        }
        cambiarIcono()
    }
    inner class DemoPageAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm){
        override fun getItem(position: Int): Fragment {
            when(position){
                0 -> return DetalleShowFragment.newInstance(showSeleccionado)
                1 -> return InformacionTecnicaFragment.newInstance(showSeleccionado)
                else -> return DetalleShowFragment.newInstance(showSeleccionado)
            }
        }
        //Numero de tabs
        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position){
                0 -> "Resumen"
                1-> "Detalle"
                else -> "Resumen"
            }
        }
    }
    fun  guardarFavorito(id:Long) {
        var preferido = db.preferidoDao().get(id)
        if (preferido != null){
            preferido.esFavorito = !preferido.esFavorito
            db.preferidoDao().modificar(preferido)
        }else{
            preferido =  ShowPreferido(0,id,true)
            db.preferidoDao().guardar(preferido)
        }
        //utilizando sharedPreferences
        /*
        val esFavorito = !shared.getBoolean(nombre, false)

        with(shared.edit()) {
            putBoolean(nombre, esFavorito)
            apply()
            cambiarIcono()
        }*/
        //Utilizando BDs

    }
    fun leerPreferido(id:Long):Boolean{
       val preferido = db.preferidoDao().get(id)
       if (preferido != null){
           return preferido.esFavorito
       } else{
           return false
       }
    }

    fun cambiarIcono(){
      GlobalScope.launch {
         /* if (shared.getBoolean(showSeleccionado.name, false)){
              favorito.setImageResource(R.drawable.ic_favorite_white_24dp)
          }else{
              favorito.setImageResource(R.drawable.ic_favorite_border_white_24dp)
          }*/
          //withContext espera a que termine la instruccion
          val esPreferido = withContext(Dispatchers.IO){leerPreferido(showSeleccionado.id.toLong())}
          if (esPreferido){
              favorito.setImageResource(R.drawable.ic_favorite_white_24dp)
          }else{
              favorito.setImageResource(R.drawable.ic_favorite_border_white_24dp)
          }
      }
    }
}