package com.demo.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.mvvm.model.MovieResponse
import com.demo.mvvm.repository.GlobalRetrofit
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class HomeModel : ViewModel() {

    var TAG = "HomeModel"
    fun getData(baseurl: String, map: Map<String, String>): MutableLiveData<MovieResponse> {
        var data = MutableLiveData<MovieResponse>()
        var call = GlobalRetrofit.getRetrofitapi(baseurl).getList(params = map)
        call.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<MovieResponse> {

                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError ${e.message}")

                }

                override fun onSuccess(t: MovieResponse) {
                    Log.d(TAG, "onNext ${t}")
                    data.value = t
                }

            })


        return data
    }
}