package com.example.missingpets.adapterRV

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.missingpets.R
import com.example.missingpets.network.recyclerPet2
import com.squareup.picasso.Picasso

class MyPostAdapter (private val dataset: List<recyclerPet2>, private val onClickListener: OnClickListener)
    : RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder>() {

    class MyPostViewHolder (val view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.vh_list_buscado, parent, false)
        Log.d("RV", "onCreateViewHolder")
        return MyPostViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        val item = dataset[position]

        holder.textView.text = item.description
        val BASE_URL = "https://sea.net.ar/missingpets/"
        val pathfile = BASE_URL + "img/" +item.photopath
        Picasso.get().load(pathfile).into(holder.imageView)
        Log.d("RV", "onBindViewHolder")

        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        Log.d("RV", "getItemCount es: "+ dataset.size.toString())
        return dataset.size
    }

    class OnClickListener(val clickListener: (pet: recyclerPet2) -> Unit) {
        fun onClick(pet: recyclerPet2) = clickListener(pet)
    }




}