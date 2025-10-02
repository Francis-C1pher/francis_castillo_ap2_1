package edu.ucne.francis_castillo_ap2_1.data.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "entradas_huacales")
data class EntradasHuacalesEntity(
    @PrimaryKey(autoGenerate = true)
    val entradaId: Int? = null,
    val fecha: Date,
    val nombreCliente: String,
    val cantidad: Int,
    val precio: Double
)
