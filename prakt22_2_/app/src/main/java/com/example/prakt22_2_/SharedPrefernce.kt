package com.example.prakt22_2_

import android.content.Context
import com.google.gson.Gson

object ConsumptionSharedPreference {
    val PREFS_NAME = "PrefsFile"
    val CONSUMPTION_KEY = "Consumptions"
    fun save(context: Context, consumptions: List<User>)
    {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(consumptions)
        editor.putString(CONSUMPTION_KEY,json)
        editor.apply()
    }
    fun load(context: Context):List<User> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(CONSUMPTION_KEY, null)
        return if (json != null) {
            gson.fromJson(json, Array<User>::class.java).toList()
        } else {
            emptyList()
        }
    }
}