package ru.stolexiy.cocktails.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object Json {
    val serializer: Gson by lazy {
        GsonBuilder().create()
    }
}
