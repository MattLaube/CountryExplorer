package com.laube.tech.countryexplorer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.laube.tech.countryexplorer.model.Country
import io.reactivex.Completable

import io.reactivex.Single

@Dao
interface CountriesDao {
    @Query("Select * from countries")
    fun fetchAllCountries() : Single<List<Country>>

    @Query("Select * from countries where name = :targetName")
    fun fetchCountry(targetName:String) : Single<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries:List<Country>) : Completable

    @Query("DELETE FROM countries")
    fun deleteAllCountries(): Completable
}