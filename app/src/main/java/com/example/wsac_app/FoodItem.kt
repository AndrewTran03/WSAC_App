package com.example.wsac_app

data class FoodItem(var name: String, var time: Int, var cost: Double, var cal: Int,
                    var ingredients: Array<String>, var instructions: Array<String>,
                    var photoId: Int?,
                    var madeTimes: Int)