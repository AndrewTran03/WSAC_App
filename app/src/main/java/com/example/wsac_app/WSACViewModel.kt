package com.example.wsac_app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class WSACViewModel (application: Application): AndroidViewModel(application) {
    //List Fragment
    //lateinit var allRecipes: LiveData<List<FoodItem>>

    //Login Fragment
    var loggedIn: Boolean = false

    //Recipe Fragment
    var currentItem: FoodItem ?= FoodItem("test", 1, 1.99, 100,
    arrayOf<String>(), arrayOf<String>(), 0, 0)

}