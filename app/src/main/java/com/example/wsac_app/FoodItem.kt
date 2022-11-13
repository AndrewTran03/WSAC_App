package com.example.wsac_app

data class FoodItem(var name: String, var time: Int, var cost: Double, var cal: Int,
                    var ingredients: ArrayList<String>, var instructions: ArrayList<String>,
                    var photoId: Int?,
                    var madeTimes: Int)