package edu.ucne.francis_castillo_ap2_1.data

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.francis_castillo_ap2_1.data.dao.EntradasHuacalesDao
import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity

@Database(
    entities = [EntradasHuacalesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entradasHuacalesDao(): EntradasHuacalesDao
}
