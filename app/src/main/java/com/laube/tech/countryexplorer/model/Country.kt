package com.laube.tech.countryexplorer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class Country (
    @PrimaryKey
    var name: String,
    var capital: String,
    var region: String,
    var population: Int,
    var flag: String
)