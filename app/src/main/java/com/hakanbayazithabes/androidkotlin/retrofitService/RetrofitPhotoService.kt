package com.hakanbayazithabes.androidkotlin.retrofitService

import android.provider.ContactsContract.Contacts.Photo
import com.hakanbayazithabes.androidkotlin.models.PhotoDelete
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitPhotoService {

    @Multipart
    @POST("/api/photos")
    suspend fun upload(
        @Part photo: MultipartBody.Part
    ): Response<Photo>

    @HTTP(method = "DELETE", path = "/api/photos", hasBody = true)
    suspend fun delete(
        @Body photoDelete: PhotoDelete
    ): Response<Unit>

}