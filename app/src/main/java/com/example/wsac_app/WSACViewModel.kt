package com.example.wsac_app

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class WSACViewModel(application: Application) : AndroidViewModel(application) {

    //Class Variables
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
    fun sortNameRegularList(sortAlphaRegList: Boolean) {
        if (sortAlphaRegList) {
            recipeList.sortBy { it.name }
            allRecipes.value = recipeList
        } else {
            recipeList.sortByDescending { it.name }
            allRecipes.value = recipeList
        }
    }
    fun sortTimeRegularList(sortAscendTimeRegList: Boolean) {
        if (sortAscendTimeRegList) {
            recipeList.sortBy { it.time }
            allRecipes.value = recipeList
        } else {
            recipeList.sortByDescending { it.time }
            allRecipes.value = recipeList
        }
    }
    fun sortCostRegularList(sortAscendCostRegList: Boolean) {
        if (sortAscendCostRegList) {
            recipeList.sortBy { it.cost }
            allRecipes.value = recipeList
        } else {
            recipeList.sortByDescending { it.cost }
            allRecipes.value = recipeList
        }
    }
    fun sortCaloriesRegularList(sortAscendCalsRegList: Boolean) {
        if (sortAscendCalsRegList) {
            recipeList.sortBy { it.cal }
            allRecipes.value = recipeList
        } else {
            recipeList.sortByDescending { it.cal }
            allRecipes.value = recipeList
        }
    }

    //Favorites Fragment
    var likedRecipes = MutableLiveData<List<FoodItem>>()
    var likedRecipeList = mutableListOf<FoodItem>()
    fun addToFavorites() {
        likedRecipeList.add(currentItem)
        likedRecipes.value = likedRecipeList
    }
    fun removeFromFavorites() {
        likedRecipeList.remove(currentItem)
        likedRecipes.value = likedRecipeList
    }
    fun inFavorites(item: FoodItem): Boolean {
        return likedRecipeList.contains(item)
    }
    fun sortNameFavList(sortAlphaFavList: Boolean) {
        if (sortAlphaFavList) {
            likedRecipeList.sortBy { it.name }
            likedRecipes.value = likedRecipeList
        } else {
            likedRecipeList.sortByDescending { it.name }
            likedRecipes.value = likedRecipeList
        }
    }
    fun sortTimeFavList(sortAscendTimeFavList: Boolean) {
        if (sortAscendTimeFavList) {
            likedRecipeList.sortBy { it.time }
            likedRecipes.value = likedRecipeList
        } else {
            likedRecipeList.sortByDescending { it.time }
            likedRecipes.value = likedRecipeList
        }
    }
    fun sortCostFavList(sortAscendCostFavList: Boolean) {
        if (sortAscendCostFavList) {
            likedRecipeList.sortBy { it.cost }
            likedRecipes.value = likedRecipeList
        } else {
            likedRecipeList.sortByDescending { it.cost }
            likedRecipes.value = likedRecipeList
        }
    }
    fun sortCaloriesFavList(sortAscendCalsFavList: Boolean) {
        if (sortAscendCalsFavList) {
            likedRecipeList.sortBy { it.cal }
            likedRecipes.value = likedRecipeList
        } else {
            likedRecipeList.sortByDescending { it.cal }
            likedRecipes.value = likedRecipeList
        }
    }

    //Preview Functionality
    var currPreviewing: Boolean = false
    var previewItem: FoodItem = FoodItem("test", 1, 1.99, 100,
        arrayOf<String>(), arrayOf<String>(), 0, 0)
    fun setPreviewFoodItem(newPreviewFoodItem: FoodItem) {
        previewItem = newPreviewFoodItem
    }
}