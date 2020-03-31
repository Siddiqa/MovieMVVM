package com.demo.mvvm.repository

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object GlobalRetrofit {

    fun getRetrofitapi(baseurl:String):RetrofitApi{
       val client=OkHttpClient.Builder()
               .readTimeout(180,TimeUnit.SECONDS)
               .connectTimeout(180,TimeUnit.SECONDS)
               .addInterceptor(LogJsonInterceptor())
               .addNetworkInterceptor(object : Interceptor{
                   override fun intercept(chain: Interceptor.Chain): Response {
                     val request=chain.request().newBuilder().build()
                       return chain.proceed(request)
                   }

               }).build()


        val requestInterface = Retrofit.Builder()
                .baseUrl(baseurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build().create(RetrofitApi::class.java)
        return requestInterface
    }
}