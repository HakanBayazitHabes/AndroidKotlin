package com.hakanbayazithabes.androidkotlin.ApiServices

import com.hakanbayazithabes.androidkotlin.consts.ApiConsts
import com.hakanbayazithabes.androidkotlin.models.ApiResponse
import com.hakanbayazithabes.androidkotlin.models.ODataModel
import com.hakanbayazithabes.androidkotlin.models.Product
import com.hakanbayazithabes.androidkotlin.retrofitService.ApiClient
import com.hakanbayazithabes.androidkotlin.retrofitService.RetrofitProductService
import com.hakanbayazithabes.androidkotlin.utility.HelperService

class ProductService {
    companion object {
        var retrofitProductServiceInterceptor = ApiClient.builderService(
            ApiConsts.apihBaseUrl,
            RetrofitProductService::class.java,
            true
        )

        suspend fun productList(page: Int): ApiResponse<ArrayList<Product>> {
            try {
                var response = retrofitProductServiceInterceptor.products(page)

                if (!response.isSuccessful) return HelperService.handlerApiError(response)

                var oDataProduct = response.body() as ODataModel<Product>

                return ApiResponse(true, oDataProduct.Value)

            } catch (e: Exception) {
                return HelperService.handleException(e)
            }
        }

        suspend fun getProductById(productId: Int): ApiResponse<Product> {
            try {
                var response = retrofitProductServiceInterceptor.getProduct(productId)

                if (!response.isSuccessful) return HelperService.handlerApiError(response)

                var oDataProduct = response.body() as ODataModel<Product>

                return ApiResponse(true, oDataProduct.Value[0])

            } catch (e: Exception) {
                return HelperService.handleException(e)
            }
        }

        suspend fun addProduct(product : Product) : ApiResponse<Product>{
            try {
                var response = retrofitProductServiceInterceptor.addProduct(product)

                if (!response.isSuccessful) return HelperService.handlerApiError(response)

                var product = response.body() as Product

                return ApiResponse(true, product)

            } catch (e: Exception) {
                return HelperService.handleException(e)
            }
        }

        suspend fun updateProduct(product: Product): ApiResponse<Unit>{
            try {
                var response = retrofitProductServiceInterceptor.updateProduct(product.Id,product)

                if (!response.isSuccessful) return HelperService.handlerApiError(response)

                return ApiResponse(true)

            } catch (e: Exception) {
                return HelperService.handleException(e)
            }
        }

        suspend fun deleteProduct(productId: Int): ApiResponse<Unit>{
            try {
                var response = retrofitProductServiceInterceptor.deleteProduct(productId)

                if (!response.isSuccessful) return HelperService.handlerApiError(response)

                return ApiResponse(true)

            } catch (e: Exception) {
                return HelperService.handleException(e)
            }
        }
    }
}