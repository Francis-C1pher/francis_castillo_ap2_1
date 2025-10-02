package edu.ucne.francis_castillo_ap2_1.data.dao

import androidx.room.*
import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntradasHuacalesDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entrada: EntradasHuacalesEntity)


    @Update
    suspend fun update(entrada: EntradasHuacalesEntity)


    @Delete
    suspend fun delete(entrada: EntradasHuacalesEntity)


    @Query("SELECT * FROM entradas_huacales")
    fun getAll(): Flow<List<EntradasHuacalesEntity>>


    @Query("SELECT * FROM entradas_huacales WHERE entradaId = :id LIMIT 1")
    fun findById(id: Int): Flow<EntradasHuacalesEntity?>
}