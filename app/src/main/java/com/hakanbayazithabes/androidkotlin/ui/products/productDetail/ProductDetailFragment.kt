package com.hakanbayazithabes.androidkotlin.ui.products.productDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.databinding.FragmentProductDetailBinding
import com.hakanbayazithabes.androidkotlin.databinding.FragmentProductListBinding

class ProductDetailFragment : Fragment() {

    val arg: ProductDetailFragmentArgs by navArgs()
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = ProductDetailFragment()
    }

    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        var root = inflater.inflate(R.layout.fragment_product_detail, container, false)
        _binding = FragmentProductDetailBinding.bind(root)




        return root
    }

}