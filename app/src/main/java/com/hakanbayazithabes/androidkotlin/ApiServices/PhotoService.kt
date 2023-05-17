package com.hakanbayazithabes.androidkotlin.ApiServices

import android.net.Uri
import com.hakanbayazithabes.androidkotlin.consts.ApiConsts
import com.hakanbayazithabes.androidkotlin.models.ApiResponse
import com.hakanbayazithabes.androidkotlin.models.Photo
import com.hakanbayazithabes.androidkotlin.models.PhotoDelete
import com.hakanbayazithabes.androidkotlin.retrofitService.ApiClient
import com.hakanbayazithabes.androidkotlin.retrofitService.RetrofitPhotoService
import com.hakanbayazithabes.androidkotlin.utility.GlobalApp
import com.hakanbayazithabes.androidkotlin.utility.HelperService
import com.hakanbayazithabes.androidkotlin.utility.RealPathUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PhotoService {
    companion object {
        private var retrofitPhotoServiceInterceptor =
            ApiClient.builderService(
                ApiConsts.photoBaseUrl,
                RetrofitPhotoService::class.java,
                false
            )

        suspend fun uploadPhoto(filterUri: Uri): ApiResponse<Photo> {

            try {
                var path = RealPathUtil.getRealPath(GlobalApp.getContext(), filterUri)

                var file = File(path)

                var requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

                var body = MultipartBody.Part.createFormData("photo", file.name, requestFile)

                var response = retrofitPhotoServiceInterceptor.upload(body)

                if (!response.isSuccessful) return HelperService.handlerApiError(response)

                return ApiResponse(true, response.body() as Photo)

            } catch (e: Exception) {
                return HelperService.handleException(e)
            }
        }

        suspend fun deletePhoto(photoDelete: PhotoDelete): ApiResponse<Unit> {
            try {
                var response = retrofitPhotoServiceInterceptor.delete(photoDelete)

                if (!response.isSuccessful) return HelperService.handlerApiError(response)

                return ApiResponse(true)

            } catch (e: Exception) {
                return HelperService.handleException(e)
            }
        }
    }
}