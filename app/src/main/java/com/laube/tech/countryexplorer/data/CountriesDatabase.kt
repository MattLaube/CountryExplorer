package com.laube.tech.countryexplorer.data

import android.content.Context
import androidx.room.*
import com.laube.tech.countryexplorer.model.Country

@Database(entities = [Country::class], version = 1)
abstract class CountriesDatabase: RoomDatabase() {
    abstract fun countryDao(): CountriesDao
    companion object {

        @Volatile private var INSTANCE: CountriesDatabase? = null

        fun getInstance(context: Context): CountriesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                CountriesDatabase::class.java, "Country.db")
                .build()
    }

}