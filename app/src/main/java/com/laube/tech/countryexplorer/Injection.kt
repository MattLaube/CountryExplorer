package com.laube.tech.countryexplorer

import android.content.Context
import com.laube.tech.countryexplorer.data.CountriesDao
import com.laube.tech.countryexplorer.data.CountriesDatabase


/**
 * Enables injection of data sources.  Inspired by google as the cleanest way to get a data source
 * into a view model.
 */
object Injection {

    fun provideUserDataSource(context: Context): CountriesDao {
        val database = CountriesDatabase.getInstance(context)
        return database.countryDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = provideUserDataSource(context)
        return ViewModelFactory(dataSource)
    }
}