package com.e.aplikacjaznanylekarz.Models

import java.sql.Date

class AppointmentDto(var dtoId: Int,
                     var dateDto: String,
                     var doctorDtoId: Int,
                     var patientDtoId: Int,
                     var locationDtoId: Int,
                     var doctorDto: DoctorDto,
                     var patientDto: Int,
                     var locationDto: LocationDto) {
}