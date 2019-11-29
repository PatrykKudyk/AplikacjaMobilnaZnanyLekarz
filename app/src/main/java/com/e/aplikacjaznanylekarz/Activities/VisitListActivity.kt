package com.e.aplikacjaznanylekarz.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import com.e.aplikacjaznanylekarz.Models.Appointment
import com.e.aplikacjaznanylekarz.Models.LoginResponse
import com.e.aplikacjaznanylekarz.Models.Message
import com.e.aplikacjaznanylekarz.Models.Patient
import com.e.aplikacjaznanylekarz.MyApp
import com.e.aplikacjaznanylekarz.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_visit_list.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class VisitListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit_list)

        var loginResponse: LoginResponse = intent.getSerializableExtra("loginResponse")
                as LoginResponse

        firstNameTextView.text = loginResponse.patient.firstName
        lastNameTextView.text = loginResponse.patient.lastName

        var buttonList: ArrayList<Button> = ArrayList()

        var appointmentList = getAppointmentsList()

        if(appointmentList.size == 0){
            val tv = TextView(this)
            tv.textSize = 24f
            tv.text = "Nie jesteś zapisany na żadne wizyty"
            tv.gravity = Gravity.CENTER
            visitListLinearLayout.addView(tv)
        }
        else{

            for(appointment in appointmentList){
                val btn = Button(this)
                btn.text = appointment.date.toString()
                btn.id = appointment.id!!.toInt()
                buttonList.add(btn)
            }


            for(button in buttonList){
                visitListLinearLayout.addView(button)
                button.setOnClickListener{
                    var intent: Intent = Intent(this, ChosenVisitActivity::class.java)
                    intent.putExtra("id", button.id)
                    startActivity(intent)
                }
            }
        }

        val logOutButton: Button = this.findViewById(R.id.logOutButton)
        logOutButton.setOnClickListener {
            finish()
        }
    }

    private fun getAppointmentsList(): ArrayList<Appointment>{
        var appointmentList = ArrayList<Appointment>()

        fetchAppointments()

        while(!MyApp.isAppointmentListJsonReceived){
            Thread.sleep(100)
        }

        return MyApp.appoinmentList
    }

    private fun fetchAppointments(){

        val url = "http://onlinedocapi.eu-central-1.elasticbeanstalk.com//api/appointments"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()


        val body = client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val appointmentList = gson.fromJson(body, AppointmentList::class.java)

                MyApp.appoinmentList = appointmentList.appointments
                MyApp.isAppointmentListJsonReceived = true
                println(appointmentList)
            }

            override fun onFailure(call: Call, e: IOException) {

            }
        })

    }

    override fun onBackPressed() {

    }
}

class AppointmentList(var appointments: ArrayList<Appointment>)
