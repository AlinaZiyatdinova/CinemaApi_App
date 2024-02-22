package com.example.prakt22_2_

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.prakt22_2_.databinding.ActivityRegistrationPageBinding
import com.google.android.material.snackbar.Snackbar

class RegistrationPage : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationPageBinding
    lateinit var snackbar: Snackbar
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityRegistrationPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun registration(view: View) {
        if (binding.edittextLogin.text.toString().trim().isNotEmpty()
            && binding.edittextPassword.text.toString().trim().isNotEmpty()
            && binding.edittextRepeatpassword.text.toString().trim().isNotEmpty())
        {
            if (binding.edittextPassword.text.toString().trim() ==
                    binding.edittextRepeatpassword.text.toString().trim())
            {
                var login = binding.edittextLogin.text.toString()
                var password = binding.edittextPassword.text.toString()
                val user = User(login,password)

                val db = DBHelper(this, null)
                if (!db.checkUser("$login"))
                {
                    db.addUser(user)

                    binding.edittextLogin.text.clear()
                    binding.edittextPassword.text.clear()
                    binding.edittextRepeatpassword.text.clear()

                    val intent = Intent(this, SecondActivity::class.java)
                    intent.putExtra("login", "${binding.edittextLogin.text.trim().toString()}")
                    startActivity(intent)
                }
                else
                {
                    snackbar = Snackbar.make(view,R.string.error_checkUser, Snackbar.LENGTH_LONG)
                    snackbar.show()
                }

            }
            else
            {
                snackbar = Snackbar.make(view,R.string.error_passwords, Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
        else
        {
            snackbar = Snackbar.make(view,R.string.error_emptyfields, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }
}