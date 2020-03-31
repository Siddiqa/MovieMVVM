package com.demo.mvvm.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.demo.mvvm.R
import com.demo.mvvm.base.BaseFragment
import com.demo.mvvm.databinding.FragmentSearchBinding
import com.demo.mvvm.model.MovieResponse
import com.demo.mvvm.model.Search
import com.demo.mvvm.repository.GlobalRetrofit
import com.demo.mvvm.utils.Constant
import com.demo.mvvm.utils.MovieItemCallback
import com.demo.mvvm.viewmodel.HomeModel
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment<FragmentSearchBinding>(), MovieItemCallback {
    private val TAG = "SearchFragment"
    lateinit var binding: FragmentSearchBinding
    private lateinit var homeModel: HomeModel
    private lateinit var searchAdapter: SearchAdapter
    private val searches = ArrayList<Search>()
    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewDataBinding()
        homeModel = ViewModelProviders.of(this).get(HomeModel::class.java)

        binding.tvnodata.visibility = View.GONE
        val etsearch = binding.etsearch
        val observable =
            ObservableOnSubscribe { emitter: ObservableEmitter<String> ->
                etsearch.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable) {
                        emitter.onNext(s.toString())
                    }
                })
            }
        Observable.create(observable)
            .observeOn(AndroidSchedulers.mainThread())
            .map { s: String ->
                s.toLowerCase()
            }
            .debounce(600, TimeUnit.MILLISECONDS)
            .filter { s: String -> !s.isEmpty() }
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    Log.e(TAG, "onSubscribe: ")
                }

                override fun onNext(s: String) {
                    Log.e(TAG, "onNext: $s")
                    val map =
                        HashMap<String, String>()
                    map["apikey"] = Constant.apikey
                    map["s"] = s
                    getSearchData(map)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: " + e.message)
                }

                override fun onComplete() {
                    Log.e(TAG, "onComplete: ")
                }
            })
    }


    fun getSearchData(map: HashMap<String, String>) {
        var call = GlobalRetrofit.getRetrofitapi(Constant.baseurl).getList(params = map)
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
                    if (t.Response == "True") {
                        searches.clear()
                        binding.tvnodata.visibility = View.GONE
                        binding.tvloading.visibility = View.GONE
                        binding.searchRecv.visibility = View.VISIBLE
                        searches.addAll(t.Search)

                        if (!::searchAdapter.isInitialized) {
                            searchAdapter = SearchAdapter(
                                searches,
                                activity!!,
                                this@SearchFragment
                            )
                            binding.searchRecv.adapter = searchAdapter

                        } else {
                            searchAdapter.notifyDataSetChanged()
                        }
                    } else {
                        binding.tvnodata.visibility = View.VISIBLE
                        binding.tvloading.visibility = View.GONE
                        binding.searchRecv.visibility = View.GONE
                    }

                }

            })


    }

    override fun ItemClicked(search: Search) {
        hideKeyboard()
    }
}