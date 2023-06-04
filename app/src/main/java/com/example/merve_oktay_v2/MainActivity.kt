package com.example.merve_oktay_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.merve_oktay_v2.adapter.ProductAdapter
import com.example.merve_oktay_v2.configs.ApiClient
import com.example.merve_oktay_v2.models.Product
import com.example.merve_oktay_v2.models.Products
import com.example.merve_oktay_v2.services.DummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var product: Product
    }

    lateinit var  editTextSearch: EditText
    lateinit var productListView: ListView
    lateinit var buttonSearch: Button
    lateinit var buttonCart:Button
    lateinit var dummyService: DummyService

    var productsArray=mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dummyService = ApiClient.getClient().create(DummyService::class.java)

        editTextSearch = findViewById(R.id.editTextSearch)
        productListView = findViewById(R.id.productsListView)
        buttonSearch=findViewById(R.id.buttonSearch)
        buttonCart=findViewById(R.id.buttonCart)

        dummyService.getProducts().enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                for(item in response.body()!!.products)
                {
                    productsArray.add(item)
                }
                productListView.adapter= ProductAdapter(this@MainActivity,productsArray)
            }
            override fun onFailure(call: Call<Products>, t: Throwable) {
                Log.e("get", t.toString())
                Toast.makeText(this@MainActivity, "Internet or Server Fail", Toast.LENGTH_LONG).show()
            }
        })

        buttonSearch.setOnClickListener(buttonSearchOnClickListener)
        buttonCart.setOnClickListener(buttonCartOnClickListener)
        productListView.setOnItemClickListener { parent, view, position, id ->
            product=productsArray.get(position)
            val intent = Intent(this, ProductDetail::class.java)
            startActivity(intent)
        }
    }
    val buttonSearchOnClickListener= View.OnClickListener {
        val searchText=editTextSearch.text
        val productsSearchArray=mutableListOf<Product>()
        dummyService = ApiClient.getClient().create(DummyService::class.java)
        dummyService.search(searchText).enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                for(item in response.body()!!.products)
                {
                    productsSearchArray.add(item)
                }
                productListView.adapter=ProductAdapter(this@MainActivity,productsSearchArray)
            }
            override fun onFailure(call: Call<Products>, t: Throwable) {
                Log.e("get", t.toString())
                Toast.makeText(this@MainActivity, "Internet or Server Fail", Toast.LENGTH_LONG).show()
            }
        })
        closeKeyboard()
    }
    val buttonCartOnClickListener=View.OnClickListener {
        val intent = Intent(this, ShoppingCart::class.java)
        startActivity(intent)
    }
    private fun closeKeyboard() {

        val view = this.currentFocus

        if (view != null) {

            val manager = getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager
            manager
                .hideSoftInputFromWindow(
                    view.windowToken, 0
                )
        }
    }
}