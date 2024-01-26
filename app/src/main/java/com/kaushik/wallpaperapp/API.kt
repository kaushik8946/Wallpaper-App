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
            "key" to "40393604-97e3ed091f2c7a1a29352b92e",
            "q" to category,
            "orientation" to "vertical",
            "image_type" to "photo",
            "order" to "popular"
        )
        for ((key, value) in params) {
            urlBuilder.addQueryParameter(key, value)
        }
        println(urlBuilder)
        val url = urlBuilder.build()
        val request = builder.url(url).build()
        val response = okHttpClient.newCall(request).execute()
        return if (response.isSuccessful) {
            response.body?.string()
        } else {
            null
        }
    }
}