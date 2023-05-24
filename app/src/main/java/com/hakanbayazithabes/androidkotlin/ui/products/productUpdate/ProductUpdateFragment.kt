package com.hakanbayazithabes.androidkotlin.ui.products.productUpdate

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
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.consts.ApiConsts
import com.hakanbayazithabes.androidkotlin.databinding.FragmentProductUpdateBinding
import com.hakanbayazithabes.androidkotlin.models.Category
import com.hakanbayazithabes.androidkotlin.models.Product
import com.hakanbayazithabes.androidkotlin.ui.products.productAdd.ProductAddFragment
import com.hakanbayazithabes.androidkotlin.ui.user.UserActivity
import com.hakanbayazithabes.androidkotlin.utility.GlobalApp
import com.squareup.picasso.Picasso

class ProductUpdateFragment : Fragment() {
    private val args: ProductUpdateFragmentArgs by navArgs()
    private var fileURI: Uri? = null
    private lateinit var root: View
    private var _binding: FragmentProductUpdateBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ProductUpdateFragment()
        const val REQUEST_CODE_PICK_IMAGE = 101
        const val REQUEST_CODE_PERMISSION = 200
    }

    private lateinit var viewModel: ProductUpdateViewModel

    private fun showGallery() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            startActivityForResult(it, ProductAddFragment.REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == ProductAddFragment.REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
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
            ProductAddFragment.REQUEST_CODE_PERMISSION -> {
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
        viewModel = ViewModelProvider(this).get(ProductUpdateViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_product_update, container, false)
        _binding = FragmentProductUpdateBinding.bind(root)

        UserActivity.setLoadingStatus(viewModel, viewLifecycleOwner)
        UserActivity.setErrorStatus(viewModel, viewLifecycleOwner)

        var photoUrl = "${ApiConsts.photoBaseUrl}/${args.product.PhotoPath}"

        Picasso.get().load(photoUrl).placeholder(R.drawable.baseline_image_24)
            .error(R.drawable.baseline_image_24).into(binding.imageViewProductPick)

        binding.txtAddFragmentProductName.editText?.setText(args.product.Name)
        binding.txtAddFragmentProductColor.editText?.setText(args.product.Color)
        binding.txtAddFragmentProductStock.editText?.setText(args.product.Stock.toString())
        binding.txtAddFragmentPoductPrice.editText?.setText(args.product.Price.toString())

        viewModel.getCategories().observe(viewLifecycleOwner) {
            ArrayAdapter<Category>(
                GlobalApp.getContext(),
                android.R.layout.simple_spinner_item,
                it
            ).also { categoryAdapter ->
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                binding.spinnerAddFragmentCategories.adapter = categoryAdapter

                var selectedPosition = it.map { it.Id }.indexOf(args.product.Category_Id)

                binding.spinnerAddFragmentCategories.setSelection(selectedPosition)
            }
        }

        binding.btnProductUpdateFragment.setOnClickListener {

            var productName = binding.txtAddFragmentProductName.editText?.text.toString()
            var productPrice = binding.txtAddFragmentPoductPrice.editText?.text.toString()
            var productStock = binding.txtAddFragmentProductStock.editText?.text.toString()
            var productColor = binding.txtAddFragmentProductColor.editText?.text.toString()

            var category = binding.spinnerAddFragmentCategories.selectedItem as Category

            var updateProduct = args.product

            updateProduct.Name = productName
            updateProduct.Price = productPrice.toDouble()
            updateProduct.Stock = productStock.toInt()
            updateProduct.Color = productColor
            updateProduct.Category_Id = category.Id

            viewModel.updateProduct(updateProduct, fileURI)
                .observe(viewLifecycleOwner) {
                    if (it) {
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
                    ProductAddFragment.REQUEST_CODE_PERMISSION
                )
            } else {
                showGallery()
            }
        }

        return root
    }


}