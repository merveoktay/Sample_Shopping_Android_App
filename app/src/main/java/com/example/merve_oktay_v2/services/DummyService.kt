package com.example.merve_oktay_v2.services

import android.text.Editable
import com.example.merve_oktay_v2.models.Cart
import com.example.merve_oktay_v2.models.CartData
import com.example.merve_oktay_v2.models.CartProduct
import com.example.merve_oktay_v2.models.Products

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface DummyService {


    @GET("products?limit=10")
    fun getProducts(): Call<Products>

   @GET("products/search")
    fun search(@Query ("q") query: Editable):Call<Products>

    @GET("carts/1")
    fun getCarts(): Call<CartData>

    @POST("carts/add")
    fun postCart( @Body Cart: Cart):Call<CartData>
}