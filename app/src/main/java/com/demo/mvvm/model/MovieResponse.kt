package com.demo.mvvm.model

data class MovieResponse(
        val Response: String,
        val Search: List<Search>,
        val totalResults: String
)