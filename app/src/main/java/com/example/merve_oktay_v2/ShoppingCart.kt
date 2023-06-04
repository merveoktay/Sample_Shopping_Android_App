package com.example.merve_oktay_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.example.merve_oktay_v2.adapter.CartAdapter
import com.example.merve_oktay_v2.configs.ApiClient
import com.example.merve_oktay_v2.models.CartData
import com.example.merve_oktay_v2.models.CartProductData
import com.example.merve_oktay_v2.services.DummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingCart : AppCompatActivity() {
    lateinit var cartListView:ListView
    lateinit var dummyService: DummyService

    var cartArray=mutableListOf<CartProductData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        cartListView=findViewById(R.id.cartListView)
        dummyService = ApiClient.getClient().create(DummyService::class.java)

        dummyService.getCarts().enqueue(object : Callback<CartData>{
            override fun onResponse(call: Call<CartData>, response: Response<CartData>) {
                for(item in response.body()!!.products)
                {
                    cartArray.add(item)
                }
                cartListView.adapter=CartAdapter(this@ShoppingCart,cartArray)
            }

            override fun onFailure(call: Call<CartData>, t: Throwable) {
                Log.e("get", t.toString())
                Toast.makeText(this@ShoppingCart, "Internet or Server Fail", Toast.LENGTH_LONG).show()
            }

        })
    }
}