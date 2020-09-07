package com.laube.tech.countryexplorer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laube.tech.countryexplorer.api.CountryListAPIService
import com.laube.tech.countryexplorer.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val countryListAPIService = CountryListAPIService()
    var currentCountries = MutableLiveData<List<Country>>()
    val loadingError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()

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
                    }

                    override fun onError(e: Throwable) {
                        loadingError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                }
                ))
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

}