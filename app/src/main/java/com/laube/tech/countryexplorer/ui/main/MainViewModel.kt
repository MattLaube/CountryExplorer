package com.laube.tech.countryexplorer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laube.tech.countryexplorer.api.CountryListAPIService
import com.laube.tech.countryexplorer.data.CountriesDao
import com.laube.tech.countryexplorer.data.CountriesRepository
import com.laube.tech.countryexplorer.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(dataSource: CountriesDao?) : ViewModel() {
    private lateinit var countriesRepository: CountriesRepository
    private val countryListAPIService = CountryListAPIService()
    var currentCountries = MutableLiveData<List<Country>>()
    val loadingError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()
    private val cacheDisposable= CompositeDisposable()

    init {
        if (dataSource != null) {
            countriesRepository = CountriesRepository(dataSource)
            loadFromLocalDB()
        }
    }

    fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            countryListAPIService.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(countryList: List<Country>) {
                        currentCountries.value = countryList
                        loadingError.value = false
                        loading.value = false
                        storeToLocalDB(countryList)
                    }

                    override fun onError(e: Throwable) {
                        loadingError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                }
                ))
    }

    fun storeToLocalDB(countries: List<Country>){
        cacheDisposable.clear()
        cacheDisposable.add(
            countriesRepository.insertCountries(countries)
                .subscribeOn(Schedulers.io() )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun loadFromLocalDB(){
        cacheDisposable.clear()
        cacheDisposable.add(
            countriesRepository.fetchAllCountries()
                .subscribeOn(Schedulers.io() )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currentCountries.value = it
                    loadingError.value = false
                    loading.value = false
                },{
                    loadingError.value = true
                })
        )
    }

    override fun onCleared() {
        disposable.clear()
        cacheDisposable.clear()
        super.onCleared()
    }

}