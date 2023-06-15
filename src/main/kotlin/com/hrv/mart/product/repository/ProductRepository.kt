package com.hrv.mart.product.repository

import com.hrv.mart.apicall.APICaller
import com.hrv.mart.product.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient

@Repository
class ProductRepository (
    @Autowired
    private val webClientBuilder: WebClient.Builder,
    @Value("\${product.url}")
    private val productURL: String
)
{
    fun getProductByProductId(productId: String) =
        APICaller(webClientBuilder)
            .getData("${productURL}/${productId}", Product::class.java)
    fun getCostOfProductByProductId(productId: String) =
        getProductByProductId(productId)
            .map { it.price }
    fun createProduct(product: Product) =
        APICaller(webClientBuilder)
            .postRequest(productURL, String::class.java, product)
    fun updateProduct(product: Product) =
        APICaller(webClientBuilder)
            .putRequest(productURL, String::class.java, product)
    fun deleteProduct(productId: String) =
        APICaller(webClientBuilder)
            .deleteData("${productURL}/${productId}", String::class.java)
}