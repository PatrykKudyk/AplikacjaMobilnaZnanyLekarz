package com.e.aplikacjaznanylekarz.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.e.aplikacjaznanylekarz.R
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val loginButton: Button = this.findViewById(R.id.loginButton)
        loginButton.setOnClickListener{
//            var intent: Intent = Intent(this, VisitListActivity::class.java)
//            startActivity(intent)
            var email = emailEditText.text.toString()
            var password = passwordEditText.text.toString()
            fetchJson(email, password)
        }
    }

    private fun fetchJson(email: String, password: String){
        val url = "http://onlinedocapi.eu-central-1.elasticbeanstalk.com/api/users/login"

        val emailJson = "\"emailDto\": \"" + email + "\","
        val passwordJson = "\"passwordDto\": \"" + password + "\""
        val tempJson = "{" + emailJson + passwordJson + "}"

        val json = tempJson.trimIndent()

        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder().url(url).post(requestBody).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Nie udało się wykonać żądania")
            }
        })


    }

    override fun onBackPressed() {

    }
}
