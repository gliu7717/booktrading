package com.example.booktradinging_login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.booktradinging_login.database.DBBookTrading
import com.example.booktradinging_login.database.MyDatabaseHelper
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.Statement

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var SQhelper: MyDatabaseHelper? = null
    private var account_icon: CircleImageView? = null
    private lateinit var login  : Button
    private lateinit var register : Button
    private var forgetPassword : TextView? = null
    private lateinit var telPhone : EditText
    private lateinit var passWord : EditText
    private lateinit var remember_pass : CheckBox
    private var name  : String? = null
    private var image : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        SQhelper = MyDatabaseHelper(this, "rememberAccount", null, 1)
        account_icon = findViewById(R.id.account_icon)
        login = findViewById(R.id.login_button)
        forgetPassword = findViewById(R.id.forget_pass)
        register = findViewById(R.id.register_button)
        telPhone = findViewById(R.id.phone)
        passWord = findViewById(R.id.password)
        remember_pass = findViewById(R.id.remember_pass)
        val user= SQhelper!!.getLoginUser()
        if(user!=null)
        {
            telPhone.setText(user.phone)
            passWord.setText(user.password)
            remember_pass.setChecked(true)
        }
        register.setOnClickListener(this)
        login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v!!.id
        if (id == R.id.login_button) {
                //login()
            val input_phone = telPhone!!.text.toString()
            val input_password = passWord!!.text.toString()
           DBBookTrading.login(this, input_phone, input_password, remember_pass.isChecked) { startMainActivity() }
        } else if (id == R.id.forget_pass) {
           // val intent = Intent(this@LoginActivity, ForgetPasswordActivity::class.java)
           // startActivity(intent)
            finish()
        } else if (id == R.id.register_button) {
            val intent1 = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent1)
            finish()
        }
    }

    fun startMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putString("Phone", telPhone.text.toString())
        bundle.putString("Name", name)
        //bundle.putString("Image", image)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}