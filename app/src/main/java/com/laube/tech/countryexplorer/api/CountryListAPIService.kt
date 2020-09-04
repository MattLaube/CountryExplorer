package com.laube.tech.countryexplorer.api

import com.laube.tech.countryexplorer.model.Country
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryListAPIService {
    private val api = Retrofit.Builder()
        .baseUrl(CountryListAPIEndPoint.SERVER)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CountryListAPIEndPoint::class.java)

    fun getCountries() : Single<List<Country>>{
        return api.getCountries()
    }

}