package com.hakanbayazithabes.androidkotlin.ui.products.productAdd

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.core.content.ContextCompat
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

        const val REQUEST_CODE_PICK_IMAGE = 101
        const val REQUEST_CODE_PERMISSION = 200
    }

    private lateinit var viewModel: ProductAddViewModel
    private var _binding: FragmentProductAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var root: View
    private var fileURI: Uri? = null

    @Suppress("DEPRECATION")
    private fun showGallery() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            fileURI = intent!!.data
            binding.imageViewProductPick.setImageURI(fileURI)


        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        @Suppress("DEPRECATION")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showGallery()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ProductAddViewModel::class.java]
        root = inflater.inflate(R.layout.fragment_product_add, container, false)
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

            viewModel.addProduct(product, fileURI).observe(viewLifecycleOwner) {
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

        binding.imageViewProductPick.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    GlobalApp.getContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                @Suppress("DEPRECATION")
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSION
                )
            } else {
                showGallery()
            }
        }

        return root
    }


}