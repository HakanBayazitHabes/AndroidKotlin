package com.hakanbayazithabes.androidkotlin.ui.products.productAdd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.viewModelScope
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.databinding.FragmentProductAddBinding
import com.hakanbayazithabes.androidkotlin.models.Category
import com.hakanbayazithabes.androidkotlin.utility.GlobalApp

class ProductAddFragment : Fragment() {

    companion object {
        fun newInstance() = ProductAddFragment()
    }

    private lateinit var viewModel: ProductAddViewModel
    private var _binding: FragmentProductAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ProductAddViewModel::class.java]
        var root = inflater.inflate(R.layout.fragment_product_add, container, false)
        _binding = FragmentProductAddBinding.bind(root)

        viewModel.getCategories().observe(viewLifecycleOwner) {
            ArrayAdapter<Category>(
                GlobalApp.getContext(),
                android.R.layout.simple_spinner_item,
                it
            ).also { categoryAdapter ->
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                binding.spinnerAddFragmentCategories.adapter = categoryAdapter
            }
        }

        return root
    }


}