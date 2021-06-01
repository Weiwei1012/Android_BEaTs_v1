package com.weiwei.beats.weather
//define data classes for the weather forecast response JSON data
data class WeatherData(val name: String, val coord: Coord, val main: Temperature, val weather: List<Weather>)

data class Coord(val lon: Double, val lat: Double)

data class Temperature(val temp: Double, val humidity: Int, val temp_min: Double, val temp_max: Double, val feels_like: Double)

data class Weather(var main: String, var description: String, var icon: String)