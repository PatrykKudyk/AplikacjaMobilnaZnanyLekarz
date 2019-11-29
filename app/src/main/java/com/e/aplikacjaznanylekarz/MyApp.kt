package com.e.aplikacjaznanylekarz

import android.app.Application
import com.e.aplikacjaznanylekarz.Models.AppointmentDto
import com.e.aplikacjaznanylekarz.Models.LoginResponse
import com.e.aplikacjaznanylekarz.Models.Patient

class MyApp(): Application() {
    companion object{
        var isLoginJsonReceived: Boolean = false
        var isAppointmentListJsonReceived: Boolean = false
        lateinit var patient: Patient
        var isCorrect: Boolean = false
        lateinit var appoinmentList: Array<AppointmentDto>
    }
}