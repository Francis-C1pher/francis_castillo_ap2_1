package edu.ucne.francis_castillo_ap2_1.data.dao

import androidx.room.*
import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntradasHuacalesDao {

    // Insertar una entrada (si ya existe con el mismo id, la reemplaza)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entrada: EntradasHuacalesEntity)

    // Actualizar una entrada existente
    @Update
    suspend fun update(entrada: EntradasHuacalesEntity)

    // Eliminar una entrada
    @Delete
    suspend fun delete(entrada: EntradasHuacalesEntity)

    // Consultar todas las entradas (flujo en tiempo real)
    @Query("SELECT * FROM entradas_huacales")
    fun getAll(): Flow<List<EntradasHuacalesEntity>>

    // Buscar una entrada por ID
    @Query("SELECT * FROM entradas_huacales WHERE entradaId = :id LIMIT 1")
    fun findById(id: Int): Flow<EntradasHuacalesEntity?>
}