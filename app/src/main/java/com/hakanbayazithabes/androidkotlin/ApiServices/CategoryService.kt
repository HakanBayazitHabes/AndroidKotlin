package com.hakanbayazithabes.androidkotlin.ApiServices

import com.hakanbayazithabes.androidkotlin.consts.ApiConsts
import com.hakanbayazithabes.androidkotlin.models.ApiResponse
import com.hakanbayazithabes.androidkotlin.models.Category
import com.hakanbayazithabes.androidkotlin.models.ODataModel
import com.hakanbayazithabes.androidkotlin.retrofitService.ApiClient
import com.hakanbayazithabes.androidkotlin.retrofitService.RetrofitCategoryService
import com.hakanbayazithabes.androidkotlin.utility.HelperService

class CategoryService {
    companion object {
        private val retrofitCategoryServiceInterceptor =
            ApiClient.builderService(
                ApiConsts.apihBaseUrl,
                RetrofitCategoryService::class.java,
                true
            )

        suspend fun categoryList(): ApiResponse<ArrayList<Category>> {
            try {
                var response = retrofitCategoryServiceInterceptor.categories()

                if (!response.isSuccessful) return HelperService.handlerApiError(response)

                var oDataCategory = response.body() as ODataModel<Category>
                return ApiResponse(true, oDataCategory.Value)

            } catch (e: Exception) {
                return HelperService.handleException(e)
            }
        }

    }
}