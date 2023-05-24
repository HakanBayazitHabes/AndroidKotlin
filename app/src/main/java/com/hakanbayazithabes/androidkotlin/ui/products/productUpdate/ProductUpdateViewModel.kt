package com.hakanbayazithabes.androidkotlin.ui.products.productUpdate

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakanbayazithabes.androidkotlin.ApiServices.CategoryService
import com.hakanbayazithabes.androidkotlin.ApiServices.PhotoService
import com.hakanbayazithabes.androidkotlin.ApiServices.ProductService
import com.hakanbayazithabes.androidkotlin.models.ApiError
import com.hakanbayazithabes.androidkotlin.models.Category
import com.hakanbayazithabes.androidkotlin.models.PhotoDelete
import com.hakanbayazithabes.androidkotlin.models.Product
import com.hakanbayazithabes.androidkotlin.utility.IViewModelState
import com.hakanbayazithabes.androidkotlin.utility.LoadinState
import kotlinx.coroutines.launch

class ProductUpdateViewModel : ViewModel(), IViewModelState {
    override var loadingState: MutableLiveData<LoadinState> = MutableLiveData<LoadinState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()

    @SuppressLint("NullSafeMutableLiveData")
    fun getCategories(): LiveData<ArrayList<Category>> {
        loadingState.value = LoadinState.Loading

        var categoriesReturn = MutableLiveData<ArrayList<Category>>()

        viewModelScope.launch {
            var response = CategoryService.categoryList()

            if (response.isSuccessful) {
                categoriesReturn.value = response.success
            } else {
                errorState.value = response.fail
            }

            loadingState.value = LoadinState.Loaded

        }

        return categoriesReturn
    }


    @SuppressLint("NullSafeMutableLiveData")
    fun updateProduct(product: Product, fileUri: Uri?): LiveData<Boolean> {
        loadingState.value = LoadinState.Loading

        var statusReturn = MutableLiveData<Boolean>()

        viewModelScope.launch {

            if (fileUri != null) {

                if (!product.PhotoPath.isNullOrEmpty()) {
                    PhotoService.deletePhoto(PhotoDelete(product.PhotoPath))
                }

                var photoResponse = PhotoService.uploadPhoto(fileUri)

                if (photoResponse.isSuccessful) {
                    product.PhotoPath = photoResponse.success!!.url
                } else {
                    errorState.value = photoResponse.fail
                }
            }


            var response = ProductService.updateProduct(product)

            if (response.isSuccessful) {
                statusReturn.value = true
            } else {
                errorState.value = response.fail
            }
        }

        return statusReturn
    }


}