package pl.maniak.catfacts

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response<T>(val all: T)