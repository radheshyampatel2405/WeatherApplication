package com.example.weatherapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.api.Constant
import com.example.weatherapplication.api.NetworkResponse
import com.example.weatherapplication.api.RetrofitInstance
import com.example.weatherapplication.api.weatherModel
import kotlinx.coroutines.launch

class WeatherVewModel : ViewModel(){
// get data from retrofit.

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<weatherModel>>()
    val weatherResult : LiveData<NetworkResponse<weatherModel>> = _weatherResult

    fun getData(city : String){
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try{
                val response = weatherApi.getWeather(Constant.api_key,city)
                if(response.isSuccessful){
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                }else{
                    _weatherResult.value = NetworkResponse.Error("cant found the city ERROR 404")
                }
            }
            catch (e : Exception){
                _weatherResult.value = NetworkResponse.Error("Data off ")

            }
        }
    }
}