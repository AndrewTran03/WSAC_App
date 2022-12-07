package com.example.wsac_app

class PhotoDictionary() {
     var map: MutableMap<String, Int> = mutableMapOf(
        "mac" to R.drawable.macncheese,
        "burger" to R.drawable.burger,
        "spaghetti" to R.drawable.spaghetti,
        "taco" to R.drawable.taco
    )

    fun addToMap(key: String, value: Int) {
        map[key] = value
    }

    fun removeFromMap(key: String) {
        map.remove(key)
    }
}