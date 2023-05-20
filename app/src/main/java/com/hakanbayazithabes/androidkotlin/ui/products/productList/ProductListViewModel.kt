package com.hakanbayazithabes.androidkotlin.ui.products.productList

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
import com.hakanbayazithabes.androidkotlin.models.Product
import com.hakanbayazithabes.androidkotlin.utility.IViewModelState
import com.hakanbayazithabes.androidkotlin.utility.LoadinState
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel(), IViewModelState {
    //TODO
    override var loadingState: MutableLiveData<LoadinState> = MutableLiveData<LoadinState>()
    override var errorState: MutableLiveData<ApiError> = MutableLiveData<ApiError>()

    var products = MutableLiveData<ArrayList<Product>>()

    @SuppressLint("NullSafeMutableLiveData")
    fun getProducts(page: Int) {
        if (page == 0) {
            loadingState.value = LoadinState.Loading
        }

        viewModelScope.launch {
            var response = ProductService.productList(page)

            if (response.isSuccessful) {
                products.value = response.success!!
            } else {
                errorState.value = response.fail
            }

            if (page == 0) {
                loadingState.value = LoadinState.Loaded
            }
        }
    }


}