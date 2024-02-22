package com.example.prakt22_2_

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.view.View

class MovieDBHelper(context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "movie_database.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val quary = "CREATE TABLE movie_database (id INT PRIMARY KEY, login TEXT, title TEXT, year TEXT, image TEXT, type TEXT, released TEXT, actors TEXT, plot TEXT)"
        db!!.execSQL(quary)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        "DROP TABLE IF EXISTS " + DBContract.MovieEntry.TABLE_NAME
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    fun addMovie(movie: Movie)
    {
        val values = ContentValues()
        values.put("login", movie.login)
        values.put("title", movie.title)
        values.put("year", movie.year)
        values.put("type", movie.type)
        values.put("released", movie.released)
        values.put("actors", movie.actors)
        values.put("plot", movie.plot)
        values.put("image", movie.image)

        val db = this.writableDatabase
        db.insert("movie_database", null, values)
        db.close()
    }
    fun getMovie(title: String, login: String):Boolean
    {
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM movie_database WHERE title = '$title' AND LOGIN = '$login'",null)
        return result.moveToFirst()
    }
    @SuppressLint("Range")
    fun getInfoMovie(login: String, title: String):ArrayList<Movie> {
        val movie = ArrayList<Movie>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor =
                db.rawQuery("select * from movie_database where login = '$login' and title = '$title'",
                    null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                var login =
                    cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.LOGIN)).toString()
                var title =
                    cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.TITLE)).toString()
                var year =
                    cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.YEAR)).toString()
                var image =
                    cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.IMAGE_POSTER))
                        .toString()
                var type =
                    cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.TYPE)).toString()
                var released =
                    cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.RELEASED))
                        .toString()
                var actors =
                    cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.ACTORS)).toString()
                var plot =
                    cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.PLOT)).toString()
                movie.add(Movie(login, title, year, image, type, released, actors, plot))
                cursor.moveToNext()
            }
        }
        return movie
    }
    @SuppressLint("Range")
    fun readAllMovie(login: String): ArrayList<Movie> {
        val movie = ArrayList<Movie>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from movie_database where login = '$login'", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                var login = cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.LOGIN)).toString()
                var title = cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.TITLE)).toString()
                var year = cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.YEAR)).toString()
                var image = cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.IMAGE_POSTER)).toString()
                var type = cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.TYPE)).toString()
                var released = cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.RELEASED)).toString()
                var actors = cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.ACTORS)).toString()
                var plot = cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.PLOT)).toString()
                movie.add(Movie(login, title, year, image, type, released, actors, plot))
                cursor.moveToNext()
            }
        }
        return movie
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteMovie(movie_title: String): Boolean {
        val db = writableDatabase
        val selection = DBContract.MovieEntry.TITLE + " LIKE ?"
        val selectionArgs = arrayOf(movie_title)
        db.delete(DBContract.MovieEntry.TABLE_NAME, selection, selectionArgs)
        return true
    }

    fun deleteAllMovie(): Boolean {
            val db = writableDatabase
            db.delete("movie_database", null, null)
            return true
    }

    fun editMovieInfo(year: String, actors: String, plot: String, type: String, title:String, login:String): Boolean
    {
        val db = writableDatabase
        val result = db.rawQuery("UPDATE movie_database SET year='$year', actors='$actors', plot='$plot', type='$type' WHERE title = '$title' AND LOGIN = '$login'",null)
        return result.moveToFirst()
    }
}