package com.example.prakt22_2_

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.Room

class DBHelper (val context: Context, val factory: SQLiteDatabase.CursorFactory?):SQLiteOpenHelper ///знак ? разрешает null
    (context,"users", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val quary = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, password TEXT)"
        db!!.execSQL(quary) /// !! необходимо для обработки возможного значения null
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }
    fun addUser(user: User)
    {
        val values = ContentValues()
        values.put("login", user.login)
        values.put("password", user.password)
        val db = this.writableDatabase
        db.insert("users", null, values)
        db.close()
    }
    fun getUser(login: String, password: String):Boolean //в функцию передается пароль и логин
    {
        val db = this.readableDatabase
        // поиск через запрос
        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' and password = '$password'",null)
        //выводим  true или false
        return result.moveToFirst()
    }
    fun checkUser(login: String):Boolean
    {
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login'",null)
        return result.moveToFirst()
    }
}