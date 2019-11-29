package com.e.aplikacjaznanylekarz.Activities

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.TextView
import com.e.aplikacjaznanylekarz.Models.AppointmentDto
import com.e.aplikacjaznanylekarz.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_chosen_visit.*
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class ChosenVisitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosen_visit)

        val id = intent.getSerializableExtra("id") as Int

        fetchAppointment(id)
        backButton.setOnClickListener{
            finish()
        }
    }

    private fun fetchAppointment(id: Int){
        val url = "http://onlinedocapi.eu-central-1.elasticbeanstalk.com//api/appointments/" +
                id.toString()

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        val body = client.newCall(request).enqueue(object: Callback{

            override fun onFailure(call: Call, e: IOException) {


            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val appointment = gson.fromJson(body, Array<AppointmentDto>::class.java)

                val sep1 = "-"
                val sep2 = "T"
                val sep3 = ":"

                val parts = appointment.first().dateDto.split(sep1, sep2, sep3)

                val date = parts[3] + ":" + parts[4] + "   " + parts[2] + "/" + parts[1] +
                        "/" + parts[0]


                runOnUiThread {
                    appointmentTitleTextView.text = appointment.first().doctorDto.specializationDto
                    doctorNameTextView.text = appointment.first().doctorDto.firstNameDto + " " +
                            appointment.first().doctorDto.lastNameDto
                    dateTextView.text = date
//                    dateTextView.text = appointment.first().dateDto
                    addressTextView.text = appointment.first().locationDto.streetNameDto + " " +
                            appointment.first().locationDto.streetNumberDto.toString() + "/" +
                            appointment.first().locationDto.officeNumberDto + " " +
                            appointment.first().locationDto.cityDto
                    visitTypeTextView.text = appointment.first().doctorDto.specializationDto
                    phoneNumberTextView.text = appointment.first().doctorDto.phoneNumberDto
                }

            }
        })
    }

    override fun onBackPressed() {

    }
}
