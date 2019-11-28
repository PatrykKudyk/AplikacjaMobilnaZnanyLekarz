package com.e.aplikacjaznanylekarz.Models

import java.io.Serializable

class Patient(var id: Int,
              var email: String,
              var firstName: String,
              var lastName: String,
              var token: String): Serializable {


}