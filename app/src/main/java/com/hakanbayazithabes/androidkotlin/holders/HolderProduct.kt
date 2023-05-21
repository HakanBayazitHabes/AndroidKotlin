package com.hakanbayazithabes.androidkotlin.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hakanbayazithabes.androidkotlin.R

class HolderProduct(view: View) : RecyclerView.ViewHolder(view) {

    var txtName: TextView = view.findViewById(R.id.txt_recyclerview_product_name)
    var txtPrice: TextView = view.findViewById(R.id.txt_recyclerview_product_price)
    var txtProductCategory: TextView =
        view.findViewById(R.id.txt_recyclerview_product_category_name)
    var imgProductImage: ImageView = view.findViewById(R.id.img_recyclerview_product_photo)

}