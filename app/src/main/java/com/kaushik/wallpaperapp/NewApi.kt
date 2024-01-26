package com.kaushik.wallpaperapp

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request

object NewApi {
    private val okHttpClient = OkHttpClient()
    private val builder = Request.Builder()

    @JvmStatic
    fun main(args: Array<String>) {
        val urlBuilder = "https://pixabay.com/api".toHttpUrlOrNull()!!.newBuilder()
        val params = mapOf<String, String?>(
            "key" to "40393604-97e3ed091f2c7a1a29352b92e",
            "category" to "nature",
            "orientation" to "vertical",
            "image_type" to "photo",
            "order" to "popular"
        )

        for (i in params) {
            urlBuilder.addQueryParameter(i.key, i.value)
        }

        println(urlBuilder.toString())
        val url = urlBuilder.build()
        val request = builder.url(url).build()
        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            println("api call unsuccessfull")
            return
        }
        val responseString = response.body?.string()!!
        println(responseString)
        val responseJson = jsonRecursive(responseString)
        println(responseJson)
        var imagesString = responseJson["hits"].toString()
        imagesString = imagesString.substring(2, imagesString.length - 2)
        println(imagesString)
        println()
        val imageMapList = parseImageString(imagesString)
        println(imageMapList[0].keys)
    }
}