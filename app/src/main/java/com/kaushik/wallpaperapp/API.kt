package com.kaushik.wallpaperapp

import okhttp3.OkHttpClient
import okhttp3.Request

object API {
    val okHttpClient= OkHttpClient()
    val builder = Request.Builder()
        .header("Authorization","I3Oku4qAUNoUcxcFAvR7panfIYNa09C195PZnu63yZJqSqpc09anGMLS")
    fun getResponse(category:String) :String?{
        val request=builder
            .url("https://api.pexels.com/v1/search?query=$category&per_page=50")
            .build()
        val response= okHttpClient.newCall(request).execute()
        return if (response.isSuccessful) {
            response.body!!.string()
        } else {
            null
        }
    }
}