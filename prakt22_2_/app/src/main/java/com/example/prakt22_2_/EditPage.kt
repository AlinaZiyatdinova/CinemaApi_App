package com.example.prakt22_2_

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.prakt22_2_.databinding.ActivityEditPageBinding

class EditPage : AppCompatActivity() {
    lateinit var bindind: ActivityEditPageBinding
    lateinit var movieDBHelper: MovieDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        bindind = ActivityEditPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bindind.root)

        val arguments = intent.extras
        val login = arguments!!["login"].toString()
        val title = arguments!!["title"].toString()

        bindind.title.text = title
        movieDBHelper = MovieDBHelper(this, null)
        var result_checkMovie = movieDBHelper.getMovie("$title", "$login")
        if (result_checkMovie)
        {
            var year: String = ""
            var type: String = ""
            var actors: String = ""
            var plot: String = ""
            var items_db = movieDBHelper.getInfoMovie("$login", "$title")
            items_db.forEach {
                year = it.year.trim().toString()
                type = it.type.trim().toString()
                actors = it.actors.trim().toString()
                plot = it.plot.trim().toString()
            }
            bindind.edittextType.setText(type)
            bindind.edittextPlot.setText(plot)
            bindind.edittextActors.setText(actors)
            bindind.edittextYear.setText(year)
        }

        bindind.buttonEdit.setOnClickListener()
        {
            if (bindind.edittextType.text.isNotEmpty()&&
                    bindind.edittextYear.text.isNotEmpty()&&
                    bindind.edittextActors.text.isNotEmpty() &&
                    bindind.edittextPlot.text.isNotEmpty())
            {
                var result_editInfoMovie = movieDBHelper.editMovieInfo("${bindind.edittextYear.text.toString()}",
                "${bindind.edittextActors.text.toString()}","${bindind.edittextPlot.text.toString()}",
                "${bindind.edittextType.text.toString()}","$title", "$login")
                if (!result_editInfoMovie) {
                    var toast = Toast.makeText(this,R.string.acc_edit, Toast.LENGTH_LONG)
                    toast.show()
                }
                else
                {
                    var toast = Toast.makeText(this,R.string.error_edit, Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            else
            {
                var toast = Toast.makeText(this,R.string.error_emptyfields, Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }
}