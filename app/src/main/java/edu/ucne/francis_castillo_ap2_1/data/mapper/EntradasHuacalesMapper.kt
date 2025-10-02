package edu.ucne.francis_castillo_ap2_1.data.mapper

import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity
import edu.ucne.francis_castillo_ap2_1.domain.model.EntradasHuacales

fun EntradasHuacalesEntity.toDomain(): EntradasHuacales {
    return EntradasHuacales(
        entradaId = entradaId?:0,
        fecha = fecha,
        nombreCliente = nombreCliente,
        cantidad = cantidad,
        precio = precio
    )
}

fun EntradasHuacales.toEntity(): EntradasHuacalesEntity {
    return EntradasHuacalesEntity(
        entradaId = entradaId,
        fecha = fecha,
        nombreCliente = nombreCliente,
        cantidad = cantidad,
        precio = precio
    )
}
