package com.e.aplikacjaznanylekarz.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.e.aplikacjaznanylekarz.Models.LoginResponse
import com.e.aplikacjaznanylekarz.R

class VisitListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit_list)

        var loginResponse: LoginResponse = intent.getSerializableExtra("loginResponse")
                as LoginResponse

        val logOutButton: Button = this.findViewById(R.id.logOutButton)
        logOutButton.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {

    }
}
