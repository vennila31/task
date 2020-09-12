package com.example.systemtaskvennilag

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.systemtaskvennilag.model.Data
import com.example.systemtaskvennilag.model.prices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val dataList: MutableList<Data> = mutableListOf()
    private lateinit var myAdapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = "Prices"


        //Set up adapter

        myAdapter =   MyAdapter(dataList)

        //Set up recyclerView

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this,OrientationHelper.VERTICAL))
        recycler_view.adapter = myAdapter

        //Set up android networking

        refresh_layout.setOnRefreshListener {
            getData()
        }

        getData()

        search.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                filterList(s.toString())

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })



    }

    private fun filterList(s: String) {

        var tempList: MutableList<Data> = ArrayList()

        var toCompare: String


        for(d in dataList){

            toCompare = d.name.toLowerCase().toString().trim()
            val textLength = s?.length
            var queryText = s.toString().toLowerCase().trim({ it <= ' ' })


            if (textLength!! <= toCompare.length) {
                if (toCompare.indexOf(queryText) != -1 || queryText == "") {
                    tempList.add(d)
                }
            }

        }

        myAdapter.updateList(tempList)

    }


    private fun getData(){

        refresh_layout.isRefreshing = true

        AndroidNetworking.initialize(this)
        AndroidNetworking.get("https://api.coincap.io/v2/assets")
            .build()
            .getAsObject(prices::class.java, object : ParsedRequestListener<prices>{
                override fun onResponse(response: prices?) {
                    dataList.addAll(response!!.data)
                    myAdapter.notifyDataSetChanged()
                    refresh_layout.isRefreshing = false
                }

                override fun onError(anError: ANError?) {
                    refresh_layout.isRefreshing = false
                }

            })
    }

}

