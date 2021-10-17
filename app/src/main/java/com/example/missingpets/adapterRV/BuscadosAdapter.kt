package com.example.missingpets.adapterRV

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.missingpets.R
import com.example.missingpets.modelRV.Buscado

class BuscadosAdapter (private val context: Context, private val dataset: List<Buscado>,private val onClickListener: OnClickListener)
    : RecyclerView.Adapter<BuscadosAdapter.BuscadoViewHolder>() {

    class BuscadoViewHolder (private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuscadosAdapter.BuscadoViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.vh_list_buscado, parent, false)
        return BuscadoViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: BuscadosAdapter.BuscadoViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.message
        holder.imageView.setImageResource(item.imageResourceId)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class OnClickListener(val clickListener: (meme: Buscado) -> Unit) {
        fun onClick(meme: Buscado) = clickListener(meme)
    }
}