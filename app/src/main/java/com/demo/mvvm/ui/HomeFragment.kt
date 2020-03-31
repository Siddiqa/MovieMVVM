package com.demo.mvvm.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.demo.mvvm.R
import com.demo.mvvm.base.BaseFragment
import com.demo.mvvm.databinding.HomefragmentBinding
import com.demo.mvvm.model.Search
import com.demo.mvvm.utils.Constant
import com.demo.mvvm.utils.EndlessRecyclerOnScrollListener
import com.demo.mvvm.utils.MovieItemCallback
import com.demo.mvvm.utils.NavUtils
import com.demo.mvvm.viewmodel.HomeModel


/**
 * Created by Siddiqa on 26-1-2019.
 */
class HomeFragment : BaseFragment<HomefragmentBinding>(), MovieItemCallback {

    var TAG = "HomeFragment"
    lateinit var homefragmentBinding: HomefragmentBinding
    lateinit var homeModel: HomeModel
    lateinit var feedAdapter: FeedAdapter
    var data = ArrayList<Search>()
    var pagecount = 1
    override fun getLayoutId(): Int {
        return R.layout.homefragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homefragmentBinding = getViewDataBinding()

        homefragmentBinding.isLoading = true
        homeModel = ViewModelProviders.of(this).get(HomeModel::class.java)
        getData()

        homefragmentBinding.recvfeed.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                pagecount++
                getData()
            }

        })

        homefragmentBinding.btnsearch.setOnClickListener {
            NavUtils.AddFragment(
                SearchFragment(),
                (activity!! as MainActivity).supportFragmentManager,
                R.id.maincontainer
            )
        }


    }

    fun getData() {
        var map = HashMap<String, String>()
        map.put("apikey", Constant.apikey)
        map.put("s", "batman")
        map.put("page", pagecount.toString())
        homeModel.getData(Constant.baseurl, map).observe(this, Observer { t ->
            run {
                Log.e(TAG, "getData ${t.toString()}")
                if (t.Response.equals("True", true)) {
                    homefragmentBinding.isLoading=false
                    t.Search?.let { data.addAll(it) }
                    if (!::feedAdapter.isInitialized) {
                        feedAdapter = FeedAdapter(activity!!, this,data)
                        homefragmentBinding.recvfeed.adapter = feedAdapter
                    } else {
                        feedAdapter?.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    override fun ItemClicked(search: Search?) {
        Toast.makeText(context!!, "Item Clicked", Toast.LENGTH_SHORT).show()

    }


}