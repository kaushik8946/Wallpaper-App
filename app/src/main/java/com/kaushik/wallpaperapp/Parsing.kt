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

    val imageEntries = input.split("}, {")
    for (entry in imageEntries) {
        try {
            val map = entry
                .replace("{", "")
                .replace("}", "")
                .split(", ")
                .associate {
                    val parts = it.split("=")
                    parts[0] to when {
                        parts[1].matches(Regex("-?\\d+(\\.\\d+)?")) -> parts[1].toDouble()
                        parts[1] == "true" || parts[1] == "false" -> parts[1].toBoolean()
                        else -> parts[1]
                    }
                }
            imageList.add(map)
        } catch (e: Exception) {
        }
    }

    return imageList
}