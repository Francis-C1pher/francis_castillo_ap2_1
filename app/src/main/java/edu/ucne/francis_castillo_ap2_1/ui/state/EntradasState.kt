package edu.ucne.francis_castillo_ap2_1.ui.entradashuacales

import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity

data class EntradasState(
    val entradas: List<EntradasHuacalesEntity> = emptyList(),
    val currentEntrada: EntradasHuacalesEntity? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val nombreClienteError: String? = null,
    val cantidadError: String? = null,
    val precioError: String? = null
)
