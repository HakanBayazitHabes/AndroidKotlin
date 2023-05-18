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

class ProductListViewModel : ViewModel() {
    //TODO
}