package com.hrv.mart.product

@Document("Product")
data class Product(
    val name: String,
    val description: String,
    val images: List<String>,
    val price: Long
) {
    @Id
    var id: String = ObjectId().toString()
    constructor(name: String, description: String, images: List<String>, price: Long, id: String) :
            this(name, description, images, price) {
        this.id = id
    }
    fun setIdToDefault(): Product {
        this.id = ObjectId().toString()
        return this
    }
}
