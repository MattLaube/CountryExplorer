package com.laube.tech.countryexplorer.data

import com.laube.tech.countryexplorer.model.Country
import io.reactivex.Completable
import io.reactivex.Single

class CountriesRepository(private val countriesDao: CountriesDao) {
    fun fetchAllCountries(): Single<List<Country>>{
        return countriesDao.fetchAllCountries()
    }

    fun featchCountryByName(targetName: String): Single<Country> {
        return countriesDao.fetchCountry(targetName)
    }

    fun insertCountries(countries:List<Country>) : Completable{
        return countriesDao.insertCountries(countries)
    }

    fun deleteAllCountries(): Completable{
        return countriesDao.deleteAllCountries()
    }

}