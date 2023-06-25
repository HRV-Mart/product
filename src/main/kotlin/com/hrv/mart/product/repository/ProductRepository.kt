package com.hrv.mart.product.repository

import com.hrv.mart.apicall.APICaller
import com.hrv.mart.custompageable.Pageable
import com.hrv.mart.product.model.Product
import com.hrv.mart.product.model.QueryParams
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.server.reactive.ServerHttpResponse
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
    fun getAllProduct(response: ServerHttpResponse?, queryParams: QueryParams) =
            APICaller(webClientBuilder)
                    .getData(
                        productURL+queryParams.getQueryParamForURL(),
                        Pageable::class.java,
                        response
                    )
                    .map {
                        it as Pageable<Product>
                    }
    fun getProductByProductId(productId: String, response: ServerHttpResponse?) =
        APICaller(webClientBuilder)
            .getData(
                "${productURL}/${productId}",
                Product::class.java,
                response
            )
    fun getCostOfProductByProductId(productId: String, response: ServerHttpResponse? = null) =
        getProductByProductId(productId, response)
            .map { it.price }
    fun createProduct(product: Product, response: ServerHttpResponse) =
        APICaller(webClientBuilder)
            .postRequest(productURL, String::class.java, product, response)
    fun updateProduct(product: Product, response: ServerHttpResponse) =
        APICaller(webClientBuilder)
            .putRequest(productURL, String::class.java, product, response)
    fun deleteProduct(productId: String, response: ServerHttpResponse) =
        APICaller(webClientBuilder)
            .deleteData("${productURL}/${productId}", String::class.java, response)
}