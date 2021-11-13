package com.example.projekt

import androidx.lifecycle.ViewModel

class FirstFragmentViewModel : ViewModel() {

    var name: String = "Joska"
    var kaloria: Int = 500

    fun setTheName(newName:String){
        name = newName
    }

    fun setTheKaloria(newKaloria: Int){
        kaloria = newKaloria
    }
}