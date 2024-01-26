package com.kaushik.wallpaperapp

object Demo {
    @JvmStatic
    fun main(args: Array<String>) {
        val a = API.getResponse("navy")
        println(a)
    }
}