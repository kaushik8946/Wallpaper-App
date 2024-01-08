package com.kaushik.wallpaperapp

import com.google.gson.Gson

fun jsonRecursive(str: String): Map<String, Any> {
    lateinit var json: Map<String, Any>

    try {
        // Deserialize the JSON string as a map.
        json = Gson().fromJson(str, Map::class.java) as Map<String, Any>

        // Create a new map to store the modified values.
        val result = HashMap<String, Any>()

        // Recursively iterate over the map and convert any string values to JSON objects.
        for (entry in json) {
            if (entry.value is String) {
                result[entry.key] = jsonRecursive(entry.value.toString())
            } else {
                result[entry.key] = entry.value
            }
        }

        return result
    }
    catch (e: Exception) {
        return emptyMap()
    }
}

fun parseImageString(input: String): List<Map<String, Any>> {
    val imageList = mutableListOf<Map<String, Any>>()
    val imageListString = input.split("}, {")
    for (i in imageListString) {
        val imageMap = mutableMapOf<String, Any>()
        val imageMapString = i.split(",")
        for (j in imageMapString) {
            val entries = j.split("=")
            if (entries.size < 2) continue
            imageMap[entries[0]] = entries[1]
        }
        imageList.add(imageMap)
    }
    return imageList
}