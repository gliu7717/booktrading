package com.example.booktradinging_login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    private var skip: TextView? = null
    private var TIME = 3
    private var isSkip = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        skip = findViewById<View>(R.id.skip) as TextView

        val actionBar = supportActionBar
        actionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            if (!isSkip) {
                val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
                startActivity(intent)
                isSkip = true
                finish()
            }
        }, (TIME*1000).toLong())

        skip!!.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
            isSkip = true
            finish()
        }
    }
}