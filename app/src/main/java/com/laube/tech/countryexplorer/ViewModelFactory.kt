package com.laube.tech.countryexplorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.laube.tech.countryexplorer.data.CountriesDao
import com.laube.tech.countryexplorer.ui.main.MainViewModel

class ViewModelFactory(private val dataSource: CountriesDao?) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataSource) as T
        }
        // more to come when details page is added
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}