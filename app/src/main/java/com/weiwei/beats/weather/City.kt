package com.weiwei.beats.weather

//define data class as the data source of the spinner or recyclerview
data class City(val eName: String, val cName: String)

data class CityWeather(val name: String, val temperature_min: Double,val temperature_max: Double, val temperature_feeling: Double, val description: String, val iconName: String)