package com.example.wsac_app

import android.app.Application
import android.util.Log
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
    var currentItem: FoodItem = FoodItem("test", 1, 1.99, 100,
    arrayOf<String>(), arrayOf<String>(), 0, 0)
    fun addFoodItem() {
        //currentItem?.let { recipeList.add(it.copy()) }
        recipeList.add(currentItem.copy())
        allRecipes.value = recipeList
    }
    var switch:Boolean = true
    fun sortName() {
        if (switch) {
            recipeList.sortBy { it.name }
            allRecipes.value = recipeList
            switch = !switch
        } else {
            recipeList.sortByDescending { it.name }
            allRecipes.value = recipeList
            switch = !switch
        }
    }

    //Favorites Fragment
    var likedRecipeList = mutableListOf<FoodItem>()
    fun addToFavorites() {
        likedRecipeList.add(currentItem)
        Log.d("recipe is in favorites:", inFavorites().toString())
    }
    fun removeFromFavorites() {
        likedRecipeList.remove(currentItem)
        Log.d("recipe is in favorites:", inFavorites().toString())
    }
    fun inFavorites(): Boolean {
        return likedRecipeList.contains(currentItem)
    }
}