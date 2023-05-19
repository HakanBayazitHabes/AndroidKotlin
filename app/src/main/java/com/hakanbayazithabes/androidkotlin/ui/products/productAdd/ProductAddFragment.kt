package com.hakanbayazithabes.androidkotlin.ui.products.productAdd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.databinding.FragmentProductAddBinding
import com.hakanbayazithabes.androidkotlin.models.Category
import com.hakanbayazithabes.androidkotlin.models.Product
import com.hakanbayazithabes.androidkotlin.ui.user.UserActivity
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

        UserActivity.setLoadingStatus(viewModel, viewLifecycleOwner)
        UserActivity.setErrorStatus(viewModel, viewLifecycleOwner)

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

        binding.btnProductSave.setOnClickListener {
            //Valdiations
            var productName = binding.txtAddFragmentProductName.editText?.text.toString()
            var productPrice = binding.txtAddFragmentPoductPrice.editText?.text.toString()
            var productStock = binding.txtAddFragmentProductStock.editText?.text.toString()
            var productColor = binding.txtAddFragmentProductColor.editText?.text.toString()

            var category = binding.spinnerAddFragmentCategories.selectedItem as Category

            var product = Product(
                0,
                productName,
                productPrice.toDouble(),
                productColor,
                productStock.toInt(),
                "",
                category.Id,
                null
            )

            viewModel.addProduct(product, null).observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(activity, "Ürün Kaydedildi", Toast.LENGTH_LONG).show()
                    binding.txtAddFragmentProductName.editText?.setText("")
                    binding.txtAddFragmentPoductPrice.editText?.setText("")
                    binding.txtAddFragmentProductStock.editText?.setText("")
                    binding.txtAddFragmentProductColor.editText?.setText("")

                    findNavController().navigate(R.id.productListFragmentNav)

                }
            }
        }
        return root
    }


}