package edu.ucne.francis_castillo_ap2_1.domain.model

import java.util.Date


data class EntradasHuacales(
    val entradaId: Int = 0,
    val fecha: Date,
    val nombreCliente: String,
    val cantidad: Int,
    val precio: Double
)
