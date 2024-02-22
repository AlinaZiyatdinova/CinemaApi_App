package com.example.prakt22_2_

import android.provider.BaseColumns

object DBContract {

    class MovieEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "movie_database"
            val LOGIN = "login"
            val TITLE = "title"
            val YEAR = "year"
            val IMAGE_POSTER = "image"
            val TYPE = "type"
            val RELEASED = "released"
            val ACTORS = "actors"
            val PLOT = "plot"
        }
    }
}