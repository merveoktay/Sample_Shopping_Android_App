package com.example.merve_oktay_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.merve_oktay_v2.MainActivity.Companion.product
import com.example.merve_oktay_v2.configs.ApiClient
import com.example.merve_oktay_v2.configs.Util
import com.example.merve_oktay_v2.models.Cart
import com.example.merve_oktay_v2.models.CartData
import com.example.merve_oktay_v2.models.CartProduct
import com.example.merve_oktay_v2.services.DummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetail : AppCompatActivity() {
    lateinit var detailImageView:ImageView
    lateinit var buttonAddToCart:Button
    lateinit var detailTitleTextView:TextView
    lateinit var detailPriceTextView:TextView
    lateinit var detailRatingTextView:TextView
    lateinit var detailDescriptionTextView:TextView
    lateinit var dummyService: DummyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        dummyService = ApiClient.getClient().create(DummyService::class.java)

        detailImageView=findViewById(R.id.detailImageView)
        buttonAddToCart=findViewById(R.id.buttonAddToCart)
        detailTitleTextView=findViewById(R.id.detailTitleTextView)
        detailDescriptionTextView=findViewById(R.id.detailDescriptionTextView)
        detailPriceTextView=findViewById(R.id.detailPriceTextView)
        detailRatingTextView=findViewById(R.id.detailRatingTextView)

        Glide.with(this).load(product.images.get(0)).override(250,150).into(detailImageView)

        detailTitleTextView.setText(product.title)
        detailDescriptionTextView.setText(product.description)
        detailRatingTextView.setText(product.rating.toString())
        detailPriceTextView.setText( "${product.price.toString()} ₺")
        buttonAddToCart.setOnClickListener(buttonAddToCartOnClickListener)

    }

    val buttonAddToCartOnClickListener= View.OnClickListener {
        val pro1 = CartProduct(1, 2);
        val  proList:List<CartProduct> = mutableListOf(pro1)
           val cart=Cart(1,proList)

          //Log.d("cartDetail",cartProduct.id.toString())

         dummyService.postCart(cart).enqueue(object : Callback<CartData> {
             override fun onResponse(call: Call<CartData>, response: Response<CartData>) {
                 val jwtCartData = response.body()
                 Log.d("status", response.code().toString())// ***  status = 400  ***
                 if (jwtCartData != null) {
                     Util.cartData = jwtCartData
                     Toast.makeText(
                         this@ProductDetail,
                         "Your order has been received",
                         Toast.LENGTH_LONG
                     ).show()
                 }
             }

             override fun onFailure(call: Call<CartData>, t: Throwable) {
                 Log.e("login", t.toString())
                 Toast.makeText(this@ProductDetail, "Internet or Server Fail", Toast.LENGTH_LONG).show()
             }

         })
    }
}