package com.example.merve_oktay_v2.models

data class Cart(
    val userId: Long,
    val products: List<CartProduct>
)

data class CartProduct (
    var id: Long,
    var quantity: Long
)
