package com.example.wsac_app

import java.lang.NumberFormatException

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

    //TODO: Handle Number format exceptions for any Doubles or Ints
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

    //TODO: parse photo to map to photo id integer in a dictionary or hashmap (separate class)
    fun getPhoto(photo: String?): Int {
        return 0
    }
}