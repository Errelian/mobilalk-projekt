package com.example.projekt

import android.service.autofill.Validators.not
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    var name: String = "Joska"
    var kaloria: Int = 500
    private var _kaloriaBurned = MutableLiveData<Double>(0.1)
    private var _kaloriaChange = MutableLiveData<Boolean>(false)
    var kaloriaBurnedString =  MutableLiveData<String>("0")

    fun kaloriaChange(): LiveData<Boolean>{
        return _kaloriaChange
    }

    fun kaloriaBurned(): LiveData<Double>{
        return _kaloriaBurned
    }

    fun kaloriaBurnedString() : LiveData<String>{
        return kaloriaBurnedString
    }

    var currentKaloriaString = Transformations.map(kaloriaBurned()){
            calorie -> String.format("%.3f",calorie)
    }

    fun setTheName(newName:String){
        name = newName
    }

    fun setTheKaloria(newKaloria: Int){
        kaloria = newKaloria
    }

    fun addToBurned(burned: Double){
        _kaloriaBurned.value = burned
    }

    fun changeKaloria(){
        _kaloriaChange.value?.let{
            _kaloriaChange.value = !it
        }
    }
}