package com.example.merve_oktay_v2.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.merve_oktay_v2.R
import com.example.merve_oktay_v2.models.CartProductData


class CartAdapter(private val context: Activity, private val list: MutableList<CartProductData>) : ArrayAdapter<CartProductData>(context, R.layout.custom_cart_list, list ) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rootView = context.layoutInflater.inflate(R.layout.custom_cart_list, null, true)
        val r_title = rootView.findViewById<TextView>(R.id.r_title)
        val r_price = rootView.findViewById<TextView>(R.id.r_price)
        val r_quantity=rootView.findViewById<TextView>(R.id.r_quantity)

        val cartProductData: CartProductData = list.get(position)

        r_title.setText(cartProductData.title)
        r_quantity.setText(cartProductData.quantity.toString())
        r_price.setText("${cartProductData.price.toString()} ₺")


        return rootView
    }
}