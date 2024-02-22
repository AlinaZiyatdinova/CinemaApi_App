package com.example.prakt22_2_

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.prakt22_2_.databinding.ActivityThirdBinding
import com.google.android.material.snackbar.Snackbar


class ThirdActivity : AppCompatActivity() {
    lateinit var bindind: ActivityThirdBinding
    lateinit var movieDBHelper: MovieDBHelper
    lateinit var snackbar:Snackbar
    lateinit var adapter: ArrayAdapter<String>
    val item_array: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        bindind = ActivityThirdBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bindind.root)
        updateItems()

        bindind.llEntries.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>, view: View,
                position: Int, id: Long,
            )
            {
                val itemValue = bindind.llEntries.getItemAtPosition(position) as String
                        val builder = AlertDialog.Builder(this@ThirdActivity)
                        builder.setTitle("Вы выбрали: $itemValue")
                            .setCancelable(true)
                            .setPositiveButton("Удалить")
                            { _, _ ->
                                delItem(itemValue.trim().toString())
                            }
                            .setNegativeButton("Редактировать")
                            { _, _ ->
                                val arguments = intent.extras
                                val info_login = arguments!!["login"].toString()
                                intentEditPage("${info_login.trim().toString()}",
                                    "${itemValue.trim().toString()}")
                            }
                val dialog = builder.create()
                dialog.show()
            }
        }
        bindind.buttonAdd.setOnClickListener()
        {
            val arguments = intent.extras
            val info_login = arguments!!["login"].toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("login", "${info_login.trim().toString()}")
            startActivity(intent)
        }
    }
    fun intentEditPage(login: String, title: String)
    {
        val intent = Intent(this, EditPage::class.java)
        intent.putExtra("login", "$login")
        intent.putExtra("title", "$title")
        startActivity(intent)
    }
    fun updateItems()
    {
        movieDBHelper = MovieDBHelper(this, null)

        val arguments = intent.extras
        val info_login = arguments!!["login"].toString()

        var items_db = movieDBHelper.readAllMovie("$info_login")

        items_db.forEach {
            var string = "${it.title.toString()}"
            item_array.add(string.toString())
        }

        adapter = ArrayAdapter( this, R.layout.simple_list_item_1 ,item_array)
        bindind.llEntries.adapter = adapter

    }
    fun delItem(item_title: String)
    {
            val arguments = intent.extras
            val info_login = arguments!!["login"].toString()
            var result_getMovie = movieDBHelper.getMovie(item_title, info_login)
            if (result_getMovie) {
                val result = movieDBHelper.deleteMovie(item_title)
                item_array.clear()
                updateItems()
            }
    }
    fun delAllItems(view: View)
    {
            val result = movieDBHelper.deleteAllMovie()
            if (result) {
                item_array.clear()
                updateItems()
                snackbar = Snackbar.make(
                    view,
                    com.example.prakt22_2_.R.string.acc_delete, Snackbar.LENGTH_LONG
                )
                snackbar.show()
            }
        else
            {
                snackbar = Snackbar.make(
                    view,
                    com.example.prakt22_2_.R.string.error_delete, Snackbar.LENGTH_LONG
                )
                snackbar.show()
            }
    }
}