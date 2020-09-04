package com.laube.tech.countryexplorer.api

import com.laube.tech.countryexplorer.model.Country
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryListAPIEndPoint {

    @GET("/rest/v2/all")
    fun getCountries() : Single<List<Country>>

    companion object {
        val SERVER = "https://restcountries.eu/"
    }
}