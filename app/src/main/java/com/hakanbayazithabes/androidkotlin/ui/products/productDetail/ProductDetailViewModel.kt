package com.hakanbayazithabes.androidkotlin.ui.products.productDetail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakanbayazithabes.androidkotlin.ApiServices.ProductService
import com.hakanbayazithabes.androidkotlin.models.ApiError
import com.hakanbayazithabes.androidkotlin.models.Product
import com.hakanbayazithabes.androidkotlin.utility.IViewModelState
import com.hakanbayazithabes.androidkotlin.utility.LoadinState
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel(), IViewModelState {
    override var loadingState: MutableLiveData<LoadinState> = MutableLiveData<LoadinState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()

    @SuppressLint("NullSafeMutableLiveData")
    fun getProduct(productId: Int): LiveData<Product> {
        var productReturn = MutableLiveData<Product>()

        loadingState.value = LoadinState.Loading

        viewModelScope.launch {
            var response = ProductService.getProductById(productId)

            if (response.isSuccessful) {
                productReturn.value = response.success
            } else {
                errorState.value = response.fail
            }
            loadingState.value = LoadinState.Loaded
        }

        return productReturn
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun deleteProduct(productId: Int): LiveData<Boolean> {
        var productReturn = MutableLiveData<Boolean>()

        loadingState.value = LoadinState.Loading

        viewModelScope.launch {
            var response = ProductService.deleteProduct(productId)

            if (response.isSuccessful) {
                productReturn.value = true
            } else {
                errorState.value = response.fail
            }
            loadingState.value = LoadinState.Loaded
        }

        return productReturn
    }
}