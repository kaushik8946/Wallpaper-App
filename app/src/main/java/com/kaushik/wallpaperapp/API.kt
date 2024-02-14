package com.kaushik.wallpaperapp

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request

object API {
    private val okHttpClient = OkHttpClient()
    private val builder = Request.Builder()

    fun getResponse(category: String): String? {
        val urlBuilder = "https://pixabay.com/api".toHttpUrlOrNull()!!.newBuilder()
        val params = mapOf<String, String?>(
            "key" to API_KEY,
            "q" to category,
            "orientation" to "vertical",
            "image_type" to "photo",
            "order" to "popular",
            "per_page" to "40"
        )
        for ((key, value) in params) {
            urlBuilder.addQueryParameter(key, value)
        }
        println(urlBuilder)
        val url = urlBuilder.build()
        val request = builder.url(url).build()
        val response = okHttpClient.newCall(request).execute()
        if (response.isSuccessful) {
            val body = response.peekBody(Long.MAX_VALUE)
            val responseMap = jsonRecursive(body.string())
            val totalHits = responseMap["totalHits"].toString().toDoubleOrNull()
            println(totalHits)
            if (totalHits == 0.0) {
                return null
            }
            return response.body?.string()
        } else {
            return null
        }
    }
}