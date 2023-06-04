package com.example.merve_oktay_v2.models

data class CartData (
    val id: Long,
    val products: List<CartProductData>,
    val total: Long,
    val discountedTotal: Long,
    val userId: Long,
    val totalProducts: Long,
    val totalQuantity: Long
)

data class CartProductData (
    val id: Long,
    val title: String,
    val price: Long,
    val quantity: Long,
    val total: Long,
    val discountPercentage: Double,
    val discountedPrice: Long
)
