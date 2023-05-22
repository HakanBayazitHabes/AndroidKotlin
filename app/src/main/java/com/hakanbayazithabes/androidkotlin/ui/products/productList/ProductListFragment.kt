package com.hakanbayazithabes.androidkotlin.ui.products.productList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.adapters.ProductListRecyclerAdapter
import com.hakanbayazithabes.androidkotlin.databinding.FragmentProductListBinding
import com.hakanbayazithabes.androidkotlin.ui.user.UserActivity
import com.hakanbayazithabes.androidkotlin.utility.GlobalApp

class ProductListFragment : Fragment() {


    private lateinit var viewModel: ProductListViewModel
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    lateinit var linearLayoutManager: LinearLayoutManager
    var productListRecyclerAdapter: ProductListRecyclerAdapter? = null

    var page: Int = 0
    var isLoading = false
    var isLastPage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ProductListViewModel::class.java]
        var root = inflater.inflate(R.layout.fragment_product_list, container, false)
        _binding = FragmentProductListBinding.bind(root)

        UserActivity.setLoadingStatus(viewModel, viewLifecycleOwner)
        UserActivity.setErrorStatus(viewModel, viewLifecycleOwner)

        binding.btnProductAdd.setOnClickListener {
            it.findNavController().navigate(R.id.productAddFragmentNav)
        }

        linearLayoutManager = LinearLayoutManager(GlobalApp.getContext())

        binding.recyclerViewProducts.layoutManager = linearLayoutManager

        if (page == 0) {
            viewModel.getProducts(page)
        } else {
            binding.recyclerViewProducts.adapter = productListRecyclerAdapter
        }

        viewModel.products.observe(viewLifecycleOwner) {
            if (it.size == 0 && page != 0) {
                productListRecyclerAdapter?.removeLoading()
                isLoading = false
                isLastPage = true
            } else {
                if (page == 0) {
                    binding.recyclerViewProducts.apply {
                        productListRecyclerAdapter = ProductListRecyclerAdapter(it) { product ->
                            //Recycler içerisindeki bir item tıklandığında burası çalışacak
                        }

                        adapter = productListRecyclerAdapter
                    }
                }
                if (page != 0) {
                    productListRecyclerAdapter?.removeLoading()

                    isLoading = false

                    var isExist = productListRecyclerAdapter!!.products.contains(it[0])

                    if (!isExist) productListRecyclerAdapter!!.addProduct(it)
                }
            }
        }

        binding.recyclerViewProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                var visibleItemCount = linearLayoutManager.childCount
                var totalItemCount = linearLayoutManager.itemCount
                var firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()


                if (!isLoading && !isLastPage) {
                    Log.i(
                        "okhttp",
                        "$visibleItemCount + $firstVisibleItemPosition >=  $totalItemCount"
                    )
                    if ((visibleItemCount + firstVisibleItemPosition >= totalItemCount)) {
                        isLoading = true
                        productListRecyclerAdapter?.addLoading()
                        page += 5
                        viewModel.getProducts(page)

                    }
                }
            }
        })

        return root
    }

}