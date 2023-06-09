package com.hakanbayazithabes.androidkotlin.retrofitService

import com.hakanbayazithabes.androidkotlin.models.Category
import com.hakanbayazithabes.androidkotlin.models.ODataModel
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitCategoryService {

    @GET("/odata/categories")
    suspend fun categories(): Response<ODataModel<Category>>
}