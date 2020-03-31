package com.demo.mvvm.repository

import com.demo.mvvm.model.MovieResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*


interface RetrofitApi {


    @GET(".")
    fun  getList(@QueryMap params:Map<String,String>): Single<MovieResponse>
}