package com.e.aplikacjaznanylekarz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = this.findViewById(R.id.loginButton)
        loginButton.setOnClickListener{
            var intent: Intent = Intent(this, VisitListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {

    }
}
