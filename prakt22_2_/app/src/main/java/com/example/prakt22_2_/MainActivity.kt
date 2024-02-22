package com.example.prakt22_2_

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var login: EditText
    lateinit var password: EditText
    lateinit var checkBox: CheckBox
    lateinit var button: Button
    lateinit var snackbar: Snackbar
    lateinit var dataStore: SharedPreferences
    var prefs_name: String = "PrefersFile"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login = findViewById(R.id.edittext_login)
        password = findViewById(R.id.edittext_password)
        checkBox = findViewById(R.id.checkbox)
        button = findViewById(R.id.button_next)
        dataStore = getSharedPreferences(prefs_name, MODE_PRIVATE)
        getPreferencesData()
    }
    fun next (view:View)
    {
        if (login.text.isNotEmpty() && password.text.isNotEmpty()) {
            if (checkBox.isChecked) {
                dataStore = getPreferences(MODE_PRIVATE)
                val ed: SharedPreferences.Editor = dataStore.edit()
                ed.putString("login", login.getText().toString())
                ed.putString("password", password.getText().toString())
                ed.apply()

                val db = DBHelper (this, null)
                val isAuth = db.getUser(login.text.toString(), password.text.toString())
                if (isAuth == true)
                {
                    val intent = Intent(this, SecondActivity::class.java)
                    intent.putExtra("login", "${login.text.trim().toString()}")
                    startActivity(intent)
                }
                else
                {
                    snackbar = Snackbar.make(view,R.string.error_auth, Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }
            else {
                dataStore.edit().clear().apply()
                val db = DBHelper (this, null)
                val isAuth = db.getUser(login.text.toString(), password.text.toString())
                if (isAuth == true)
                {
                    val intent = Intent(this, SecondActivity::class.java)
                    intent.putExtra("login", "${login.text.trim().toString()}")
                    startActivity(intent)
                }
                else
                {
                    snackbar = Snackbar.make(view,R.string.error_auth, Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }
        }
        else
        {
            snackbar = Snackbar.make(view,R.string.error_emptyfields, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }

    fun registration(view:View)
    {
        val intent = Intent(this, RegistrationPage::class.java)
        startActivity(intent)
    }

    private fun getPreferencesData() {
        dataStore = getPreferences(MODE_PRIVATE);
        login.setText(dataStore.getString("login", ""));
        password.setText(dataStore.getString("password", ""));
    }
}