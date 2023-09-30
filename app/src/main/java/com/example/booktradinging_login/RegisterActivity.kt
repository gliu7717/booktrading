package com.example.booktradinging_login

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.booktradinging_login.database.DBBookTrading
import com.example.booktradinging_login.database.User
import de.hdodenhof.circleimageview.CircleImageView

class RegisterActivity : AppCompatActivity() {
    private lateinit var account_icon: CircleImageView
    private lateinit var userPhone: EditText
    private lateinit var userName: EditText
    private lateinit var userPassword1: EditText
    private lateinit var usersPassword2: EditText
    private lateinit var  register: Button
    private var phone: String? = null
    private var password_1: String? = null
    private var password_2: String? = null
    private var name: String? = null
    private val imageString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        account_icon = findViewById(R.id.account_icon)
        register = findViewById(R.id.register_button)
        userPhone = findViewById(R.id.user_phone)
        userName = findViewById(R.id.user_name)
        userPassword1 = findViewById(R.id.user_password_1)
        usersPassword2 = findViewById(R.id.user_password_2)

        register.setOnClickListener {
            password_1 = userPassword1.text.toString()
            password_2 = usersPassword2.text.toString()
            if (TextUtils.isEmpty(password_1) || TextUtils.isEmpty(password_2)) {
                Toast.makeText(this@RegisterActivity, "Please input password", Toast.LENGTH_SHORT)
                    .show()
            } else if (password_1 != password_2) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password not match.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val user =
                    User(userPhone.text.toString(), userName.text.toString(), password_1!!, "")
                DBBookTrading.insertUser(user)
            }
        }
    }
}