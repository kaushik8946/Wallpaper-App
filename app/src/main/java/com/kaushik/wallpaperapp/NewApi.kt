package com.kaushik.wallpaperapp

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request

object NewApi {
    private val okHttpClient= OkHttpClient()
    private val builder = Request.Builder()
    @JvmStatic
    fun main(args: Array<String>) {
        val urlBuilder= "https://pixabay.com/api".toHttpUrlOrNull()!!.newBuilder()
        val params= mutableMapOf<String,String?>(
            "key" to "40393604-97e3ed091f2c7a1a29352b92e",
            "q" to "navy"
        )

        for (i in params) {
            urlBuilder.addQueryParameter(i.key,i.value)

        }
        println(urlBuilder.toString())
        val url = urlBuilder.build()
        val request = builder.url(url).build()
        val response = okHttpClient.newCall(request).execute()
        if (response.isSuccessful) {
            println(response.body?.string())
        } else {
            println(response.message)
        }
    }
}