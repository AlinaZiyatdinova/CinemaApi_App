package com.example.prakt22_2_

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prakt22_2_.databinding.ActivitySecondBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject


class SecondActivity : AppCompatActivity() {
    lateinit var movie_name: EditText
    lateinit var button_search: Button
    lateinit var image_poster: ImageView
    lateinit var toast: Toast
    lateinit var binding: ActivitySecondBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movie_name = findViewById(R.id.edittext_namemovie)
        button_search = findViewById(R.id.button_search)
        image_poster = findViewById(R.id.image_poster)
        val arguments = intent.extras
        val info_login = arguments!!["login"].toString()
        binding.buttonSearch.setOnClickListener()
        {
            if (movie_name.text.isNotEmpty()) {
                var url =
                    "https://www.omdbapi.com/?apikey=36de77d8&t="+movie_name.text
                val queue = Volley.newRequestQueue(this)
                val stringR = StringRequest(
                    com.android.volley.Request.Method.GET,
                    url,
                    { response ->
                        val obj = JSONObject(response)
                        var title = getString(R.string.title_year)
                        var info_year = obj.getString("Year").toString()
                        var string_info_year = "$title $info_year".toString()

                        title = getString(R.string.title_name)
                        var info_title = obj.getString("Title").toString()
                        var string_info_title = "$title $info_title".toString()

                        title = getString(R.string.title_type)
                        var info_type = obj.getString("Type").toString()
                        var string_info_type = "$title $info_type".toString()

                        title = getString(R.string.title_released)
                        var info_released = obj.getString("Released").toString()
                        var string_info_released = "$title $info_released".toString()

                        title = getString(R.string.title_actors)
                        var info_actors = obj.getString("Actors").toString()
                        var string_info_actors = "$title $info_actors".toString()

                        title = getString(R.string.title_plot)
                        var info_plot = obj.getString("Plot").toString()
                        var string_info_plot = "$title $info_plot".toString()

                        var listview_array = ArrayList<String>()
                        listview_array.add("$string_info_title")
                        listview_array.add("$string_info_type")
                        listview_array.add("$string_info_released")
                        listview_array.add("$string_info_actors")
                        listview_array.add("${string_info_plot}")

                        val adapter = ArrayAdapter(this, R.layout.list_item, R.id.textView_item, listview_array)
                        binding.listviewPlot.adapter = adapter

                        var info_image = obj.getString("Poster").toString()
                        Picasso.get().load(info_image).into(image_poster)

                        Log.d("MyLog", "Response:$response")


                        val db = MovieDBHelper (this, null)
                        val isStock = db.getMovie(info_title.toString(), info_login.toString())

                        if (isStock == false)
                        {
                            val movie = Movie("$info_login", "$info_title", "$info_year", "$info_image",
                                "$info_type", "$info_released", "$info_actors", "$info_plot")
                            db.addMovie(movie)
                        }
/*                        Thread {
                            db.getDao().insertItem(item)
                        }.start()*/
                    },
                    {
                        Log.d("MyLog", "Volley error:$it")
                    })
                queue.add(stringR)
            }
            else
            {
                toast = Toast.makeText(this,"Введите название фильма", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }
    fun nextpage (view: View)
    {
        val arguments = intent.extras
        val info_login = arguments!!["login"].toString()
        val intent = Intent(this, ThirdActivity::class.java)
        intent.putExtra("login", "${info_login.trim().toString()}")
        startActivity(intent)
    }
}