package com.cesarpita.videosap

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cesarpita.videosap.DetalleShowActivity.Companion.SHOW
import com.cesarpita.videosap.daos.AppDatabase
import com.cesarpita.videosap.modelos.ContenerdorShow
import com.cesarpita.videosap.modelos.Show
import com.cesarpita.videosap.serviciosRest.Servicio
import com.cesarpita.videosap.serviciosRest.tvmaze
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.show_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private var peliculas:ArrayList<Show> = arrayListOf()
    lateinit private var servicios : tvmaze
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //llamado desde archivo Json
        /* val tipoContenedor = object : TypeToken<List<ContenerdorShow>>(){}.type
        val contendorShows = Gson().fromJson<List<ContenerdorShow>>(loadJSONFromAsset(this), tipoContenedor)
        for (contenedor in contendorShows){
            peliculas.add(contenedor.show)
        }*/
        //Consumiendo el servicio
        db = AppDatabase.getDatabase(this)
        servicios = Servicio().getTvmaze()

        listarPeliculas("polic")
        //cuando cambia el texto
        /*  //para edit text
     filtro.addTextChangedListener(object :TextWatcher{
         override fun afterTextChanged(s: Editable?) {
         }
         override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
         }
         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
             (lista_show.adapter as RecyclerShowAdapter).filter.filter(s)
         } })*/
        filtro.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
          //solo se lista las peliculas cuandos e presiona el boton buscar
        override fun onQueryTextSubmit(query: String?): Boolean {
              Toast.makeText(this@MainActivity, R.string.buscando, Toast.LENGTH_LONG).show()
              //(lista_show.adapter as RecyclerShowAdapter).filter.filter(query)
              query?.let {
                  listarPeliculas(it)
              }
              return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
              //(lista_show.adapter as RecyclerShowAdapter).filter.filter(newText)
              //se comenta porque no queremos que vaya a consumir cada vez que se pulsa una letra
              return false
        }
      })
    }
    //Adaptador
    inner class RecyclerShowAdapter(private val peliculas:ArrayList<Show>):
        RecyclerView.Adapter<RecyclerShowAdapter.ShowHolder>(), Filterable {

        var peliculasFiltradas: MutableList<Show> = arrayListOf()
        init{
            peliculasFiltradas = peliculas
        }

        inner class ShowHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            fun bind(show: Show )=with(itemView){
                titulo_show.text = show.name
                if(show.summary !=null) {
                    desc_show.text = show.summary.toSpanned()
                }
                if (show.image != null) {
                    val url = show.image.medium
                    Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.ic_baseline_image_grey)
                        .into(portada_show);
                }
                itemView.setOnClickListener{
                    val intent = Intent(this@MainActivity, DetalleShowActivity::class.java)
                    intent.putExtra(SHOW, show)
                    startActivity(intent)
                }
            }
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.show_item, parent, false)
            return ShowHolder(view)
        }
        //NUmero de regsitros
        override fun getItemCount(): Int {
            return peliculasFiltradas.size
        }

        override fun onBindViewHolder(holder: ShowHolder, position: Int) {
           holder.bind(peliculasFiltradas.get(position))
        }

        override fun getFilter(): Filter {
            return object :Filter(){
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val palabraParaBuscar = constraint.toString()
                    val resultadoFiltro = ArrayList<Show>()
                    for (show in peliculas){
                        if (show.name.toLowerCase().contains(palabraParaBuscar.toLowerCase())){
                            resultadoFiltro.add(show)
                        }
                    }
                    val filterResults = FilterResults()
                    filterResults.values = resultadoFiltro
                    return  filterResults
                }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                   peliculasFiltradas = filterResults?.values as MutableList<Show>
                    notifyDataSetChanged()
                    if (peliculasFiltradas.isEmpty()){
                        txtno_encontrado.visibility = View.VISIBLE
                    }else{
                        txtno_encontrado.visibility = View.GONE
                    }
                }
            }
        }
    }

    fun loadJSONFromAsset(context: Context): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = context.getAssets().open("shows.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun String.toSpanned(): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(this)
        }
    }
   //Evento onback boton atras, mostras dialog antes de cerrar
    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this)
        //builder.setTitle("Cerrar")
        builder.setMessage(getString(R.string.mensaje_salir))
       builder.setPositiveButton("Si"){
           dialog, which ->    super.onBackPressed()
           //Toast.makeText(applicationContext, "Si, saldra", Toast.LENGTH_SHORT).show()
       }
       builder.setNegativeButton("No"){
           //para cerrar el dialog
           dialog, which -> dialog.dismiss()
       }
       val dialog:AlertDialog = builder.create()
       dialog.show()
    }

    fun listarPeliculas(filtro:String) {
        cargando.visibility = View.VISIBLE
        lista_show.visibility = View.GONE
        servicios.listarPeliculas(filtro).enqueue(object : Callback<List<ContenerdorShow>> {
            override fun onFailure(call: Call<List<ContenerdorShow>>, t: Throwable) {
                cargando.visibility = View.GONE
                lista_show.visibility = View.VISIBLE
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()

                GlobalScope.launch {
                    //trabaja con el contexto de la interfaz de usuario
                    //peliculas = listarPelicularDB() as ArrayList<Show>
                    peliculas = listaPeliculasDB(filtro) as ArrayList<Show>
                    withContext(Dispatchers.Main) {
                        lista_show.layoutManager = GridLayoutManager(this@MainActivity, 3)
                        lista_show.adapter = RecyclerShowAdapter(peliculas)
                        if (peliculas.isEmpty()){
                            txtno_encontrado.visibility = View.VISIBLE
                        }else{
                            txtno_encontrado.visibility = View.GONE
                        }
                    }

                }
            }
            //Peticion asincrona, por eso el adapter debe estar dentro del onResponse, si esta fuera sale sin datos las app
            override fun onResponse(
                call: Call<List<ContenerdorShow>>,
                response: Response<List<ContenerdorShow>>
            ) {
                cargando.visibility = View.GONE
                lista_show.visibility = View.VISIBLE
                if (response.isSuccessful) {
                    peliculas.clear()
                    val contenerdorShow = response.body()
                    for (contenedor in contenerdorShow!!) {
                        peliculas.add(contenedor.show)
                    }
                    if (peliculas.isEmpty()){
                        txtno_encontrado.visibility = View.VISIBLE
                    }else{
                        txtno_encontrado.visibility = View.GONE
                    }
                    GlobalScope.launch {
                        guardarPeliculasDB(peliculas)
                    }
                }else{
                    //GlobalScope nos permite incorporar CoRutinas
                    GlobalScope.launch {
                        peliculas = listarPelicularDB() as ArrayList<Show>
                    }
                }
                lista_show.layoutManager = GridLayoutManager(this@MainActivity, 3)
                lista_show.adapter = RecyclerShowAdapter(peliculas)
            }
        })
    }

    fun guardarPeliculasDB(peliculas:List<Show>){
        db.showDao().guardarList(peliculas)
    }

    fun listarPelicularDB():List<Show>{
        return db.showDao().listPeliculas()
    }

    fun listaPeliculasDB(filtro: String):List<Show>{
        return db.showDao().listaPeliculas(filtro)
    }
    }