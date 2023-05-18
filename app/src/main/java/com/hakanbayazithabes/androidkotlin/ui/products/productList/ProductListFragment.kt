package com.hakanbayazithabes.androidkotlin.ui.products.productList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {

    companion object {
        fun newInstance() = ProductListFragment()
    }

    private lateinit var viewModel: ProductListViewModel
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_product_list, container, false)
        _binding = FragmentProductListBinding.bind(root)

        binding.btnProductAdd.setOnClickListener {
            it.findNavController().navigate(R.id.productAddFragmentNav)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}