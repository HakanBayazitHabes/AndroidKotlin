package com.hakanbayazithabes.androidkotlin.ui.products.productDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.hakanbayazithabes.androidkotlin.R
import com.hakanbayazithabes.androidkotlin.consts.ApiConsts
import com.hakanbayazithabes.androidkotlin.databinding.FragmentProductDetailBinding
import com.hakanbayazithabes.androidkotlin.databinding.FragmentProductListBinding
import com.hakanbayazithabes.androidkotlin.models.Product
import com.hakanbayazithabes.androidkotlin.ui.user.UserActivity
import com.squareup.picasso.Picasso

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

        UserActivity.setLoadingStatus(viewModel, viewLifecycleOwner)
        UserActivity.setErrorStatus(viewModel, viewLifecycleOwner)


        var p: (Product?) -> Unit = {
            if (it != null) {
                binding.txtProductName.text = it.Name
                binding.txtProductColor.text = it.Color
                binding.txtProductPrice.text = it.Price.toString()
                binding.txtProductStock.text = it.Stock.toString()
                binding.txtProductCategoryName.text = it.Category?.Name
                var fullPhotoUrl = "${ApiConsts.photoBaseUrl}/${it.PhotoPath}"

                Picasso.get().load(fullPhotoUrl).placeholder(R.drawable.baseline_image_24)
                    .error(R.drawable.baseline_image_24).into(binding.imageProductPicture)

            }
        }

        viewModel.getProduct(arg.productId).observe(viewLifecycleOwner, p)

        binding.btnProductDelete.setOnClickListener {
            viewModel.deleteProduct(arg.productId).observe(viewLifecycleOwner) {
                if (it) {
                    Snackbar.make(
                        root,
                        "ID'si ${arg.productId} olan ürün silinmiştir",
                        Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.productListFragmentNav)
                }
            }
        }

        return root
    }

}