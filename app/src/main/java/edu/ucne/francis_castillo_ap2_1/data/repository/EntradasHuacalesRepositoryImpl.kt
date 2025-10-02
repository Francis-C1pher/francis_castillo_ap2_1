package edu.ucne.francis_castillo_ap2_1.data.repository

import edu.ucne.francis_castillo_ap2_1.data.dao.EntradasHuacalesDao
import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.firstOrNull

@Singleton
class EntradasHuacalesRepositoryImpl @Inject constructor(
    private val dao: EntradasHuacalesDao
) : EntradasHuacalesRepository {

    override fun getAllEntradas(): Flow<List<EntradasHuacalesEntity>> {
        return dao.getAll()
    }

    override suspend fun getEntradaById(entradaId: Int): EntradasHuacalesEntity? {

        return dao.findById(entradaId).firstOrNull()
    }

    override suspend fun insertEntrada(entrada: EntradasHuacalesEntity) {
        dao.insert(entrada)
    }

    override suspend fun updateEntrada(entrada: EntradasHuacalesEntity) {
        dao.update(entrada)
    }

    override suspend fun deleteEntrada(entrada: EntradasHuacalesEntity) {
        dao.delete(entrada)
    }

    override fun searchEntradas(query: String): Flow<List<EntradasHuacalesEntity>> {

        return dao.getAll()
    }
}