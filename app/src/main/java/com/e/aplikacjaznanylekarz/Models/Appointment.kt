package com.e.aplikacjaznanylekarz.Models

import java.sql.Date

class Appointment(var id: Int,
                  var date: Date,
                  var doctorId: Int,
                  var patientId: Int,
                  var locationId: Int) {
}