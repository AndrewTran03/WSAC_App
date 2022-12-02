package com.example.wsac_app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WSACViewModel (application: Application): AndroidViewModel(application) {
    //List Fragment
    var allRecipes = MutableLiveData<List<FoodItem>>()
    var recipeList = mutableListOf<FoodItem>()

    //Login Fragment
    var loggedIn: Boolean = false

    //Recipe Fragment
    var currentItem: FoodItem ?= FoodItem("test", 1, 1.99, 100,
    arrayOf<String>(), arrayOf<String>(), 0, 0)
    fun addFoodItem() {
        currentItem?.let { recipeList.add(it) }
        allRecipes.value = recipeList
    }

}