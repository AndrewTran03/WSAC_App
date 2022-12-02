package com.example.wsac_app

import java.lang.NumberFormatException

var photoMap = mapOf("mac" to R.drawable.macncheese,
                    "burger" to R.drawable.burger,
                    "spaghetti" to R.drawable.spaghetti,
                    "taco" to R.drawable.taco)
class Parser {
    fun getTitle(title: String): String {
        return title
    }

    fun getTime(time: String): Int {
        val t: Int
        t = try {
            time.toInt()
        } catch (e: NumberFormatException) {
            -1
        }
        return t
    }

    fun getCost(cost: String): Double {
        val c: Double
        c = try {
            cost.toDouble()
        } catch (e: NumberFormatException) {
            -1.00
        }
        return c
    }

    fun getCal(cal: String): Int {
        val l: Int
        l = try {
            cal.toInt()
        } catch (e: NumberFormatException) {
            -1
        }
        return l
    }

    fun getIngredients(ingredients: String): Array<String> {
        return ingredients.split("\n".toRegex()).toTypedArray()
    }

    fun getInstructions(instructions: String): Array<String> {
        return instructions.split("\n".toRegex()).toTypedArray()
    }

    fun getPhoto(keyword: String): Int {
        for ((key, value) in photoMap) {
            if(keyword.contains(key)) {
                return value
            }
        }
        return R.drawable.mac
    }
}