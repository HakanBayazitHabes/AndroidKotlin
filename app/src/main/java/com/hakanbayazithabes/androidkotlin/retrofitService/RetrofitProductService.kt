package com.hakanbayazithabes.androidkotlin.retrofitService

import com.hakanbayazithabes.androidkotlin.models.ODataModel
import com.hakanbayazithabes.androidkotlin.models.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitProductService {

    // /odata/products?$expand=category($select=id,name,stock,price,photoPath&$orderbyid desc&$skip=10)
    @GET("/odata/products?\$expand=category(\$select=Name)&\$select=id,name,stock,price,photoPath&\$orderby id desc")
    suspend fun products(
        @Query("\$skip") page: Int
    ): Response<ODataModel<Product>>

    @GET("/odata/products({productId})")
    suspend fun getProduct(
        @Path("productId") productId: Int
    ): Response<ODataModel<Product>>

    @POST("/odata/products")
    suspend fun addProduct(
        @Body product: Product
    ): Response<Product>

    @PUT("/odata/products({productId})")
    suspend fun updateProduct(
        @Body product: Product,
        @Path("productId") productId: Int
    ): Response<Unit>

    @DELETE("/odata/products({productId})")
    suspend fun deleteProduct(
        @Path("productId") productId: Int
    ): Response<Unit>
}