package com.example.wsac_app

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class WSACViewModel (application: Application): AndroidViewModel(application) {

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
    private var sortAlphaRegList: Boolean = true
    fun sortNameRegularList() {
        if (sortAlphaRegList) {
            recipeList.sortBy { it.name }
            allRecipes.value = recipeList
            sortAlphaRegList = !sortAlphaRegList
        } else {
            recipeList.sortByDescending { it.name }
            allRecipes.value = recipeList
            sortAlphaRegList = !sortAlphaRegList
        }
    }
    private var sortAscendCostRegList: Boolean = true
    fun sortCostRegularList() {
        if (sortAscendCostRegList) {
            recipeList.sortBy { it.cost }
            allRecipes.value = recipeList
            sortAscendCostRegList = !sortAscendCostRegList
        } else {
            recipeList.sortByDescending { it.cost }
            allRecipes.value = recipeList
            sortAscendCostRegList = !sortAscendCostRegList
        }
    }
    private var sortAscendCalsRegList: Boolean = true
    fun sortCaloriesRegularList() {
        if (sortAscendCalsRegList) {
            recipeList.sortBy { it.cal }
            allRecipes.value = recipeList
            sortAscendCalsRegList = !sortAscendCalsRegList
        } else {
            recipeList.sortByDescending { it.cal }
            allRecipes.value = recipeList
            sortAscendCalsRegList = !sortAscendCalsRegList
        }
    }

    //Favorites Fragment
    var likedRecipes = MutableLiveData<List<FoodItem>>()
    var likedRecipeList = mutableListOf<FoodItem>()
    fun addToFavorites() {
        likedRecipeList.add(currentItem)
        Log.d("recipe is in favorites:", inFavorites().toString())
        likedRecipes.value = likedRecipeList
    }
    fun removeFromFavorites() {
        likedRecipeList.remove(currentItem)
        Log.d("recipe is in favorites:", inFavorites().toString())
        likedRecipes.value = likedRecipeList
    }
    fun inFavorites(): Boolean {
        return likedRecipeList.contains(currentItem)
    }
    private var sortAlphaFavList: Boolean = true
    fun sortNameFavList() {
        if (sortAlphaFavList) {
            likedRecipeList.sortBy { it.name }
            likedRecipes.value = likedRecipeList
            sortAlphaFavList = !sortAlphaFavList
        } else {
            likedRecipeList.sortByDescending { it.name }
            likedRecipes.value = likedRecipeList
            sortAlphaFavList = !sortAlphaFavList
        }
    }
    private var sortAscendCostFavList: Boolean = true
    fun sortCostFavList() {
        if (sortAscendCostRegList) {
            likedRecipeList.sortBy { it.cost }
            likedRecipes.value = likedRecipeList
            sortAscendCostFavList = !sortAscendCostFavList
        } else {
            likedRecipeList.sortByDescending { it.cost }
            likedRecipes.value = likedRecipeList
            sortAscendCostFavList = !sortAscendCostFavList
        }
    }
    private var sortAscendCalsFavList: Boolean = true
    fun sortCaloriesFavList() {
        if (sortAscendCalsFavList) {
            likedRecipeList.sortBy { it.cal }
            likedRecipes.value = likedRecipeList
            sortAscendCalsFavList = !sortAscendCalsFavList
        } else {
            likedRecipeList.sortByDescending { it.cal }
            likedRecipes.value = likedRecipeList
            sortAscendCalsFavList = !sortAscendCalsFavList
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