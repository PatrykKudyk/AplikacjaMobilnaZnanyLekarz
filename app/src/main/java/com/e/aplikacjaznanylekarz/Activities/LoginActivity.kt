package com.e.aplikacjaznanylekarz.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.e.aplikacjaznanylekarz.Models.LoginResponse
import com.e.aplikacjaznanylekarz.Models.Message
import com.e.aplikacjaznanylekarz.Models.Patient
import com.e.aplikacjaznanylekarz.MyApp
import com.e.aplikacjaznanylekarz.R
import com.google.gson.GsonBuilder
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

            if(!emailEditText.text.toString().equals("") ||
                !passwordEditText.text.toString().equals("")){
                var email = emailEditText.text.toString()
                var password = passwordEditText.text.toString()
                var loginResponse = getResponse(email, password)
                if(loginResponse.isCorrect){
                    MyApp.isLoginJsonReceived = false
                    var intent = Intent(this, VisitListActivity::class.java)
                    intent.putExtra("loginResponse", loginResponse)
                    emailEditText.text.clear()
                    passwordEditText.text.clear()
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Niepoprawne dane logowania",
                        Toast.LENGTH_SHORT).show()
                    emailEditText.text.clear()
                    passwordEditText.text.clear()
                    MyApp.isLoginJsonReceived = false
                    MyApp
                }
            }else{
                Toast.makeText(this, "Przed zalogowaniem wypełnij pola",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getResponse(email: String, password: String): LoginResponse{

        fetchJson(email, password)

        while(!MyApp.isLoginJsonReceived){
            Thread.sleep(100)
        }

        return LoginResponse(MyApp.patient, MyApp.isCorrect)
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


        val body = client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()


                val message = gson.fromJson(body, Message::class.java)
                if(message.message == null){
                    val patientReceived = gson.fromJson(body, Patient::class.java)
                    MyApp.patient = patientReceived
                    MyApp.isCorrect = true
                    MyApp.isLoginJsonReceived = true
                }else{
                    MyApp.isCorrect = false
                    MyApp.patient = Patient(0, "", "", "", "")
                    MyApp.isLoginJsonReceived = true
                }

                //println(body)
            }

            override fun onFailure(call: Call, e: IOException) {

               // println("Nie udało się wykonać żądania")
            }
        })

    }

    override fun onBackPressed() {

    }
}
