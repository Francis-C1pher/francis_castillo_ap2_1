package edu.ucne.francis_castillo_ap2_1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.francis_castillo_ap2_1.data.AppDatabase
import edu.ucne.francis_castillo_ap2_1.data.dao.EntradasHuacalesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "huacales_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideEntradasHuacalesDao(db: AppDatabase): EntradasHuacalesDao {
        return db.entradasHuacalesDao()
    }
}
