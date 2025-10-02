package edu.ucne.francis_castillo_ap2_1.data.repository

import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity
import kotlinx.coroutines.flow.Flow

interface EntradasHuacalesRepository {
    fun getAllEntradas(): Flow<List<EntradasHuacalesEntity>>
    suspend fun getEntradaById(entradaId: Int): EntradasHuacalesEntity?
    suspend fun insertEntrada(entrada: EntradasHuacalesEntity)
    suspend fun updateEntrada(entrada: EntradasHuacalesEntity)
    suspend fun deleteEntrada(entrada: EntradasHuacalesEntity)
    fun searchEntradas(query: String): Flow<List<EntradasHuacalesEntity>>
}
