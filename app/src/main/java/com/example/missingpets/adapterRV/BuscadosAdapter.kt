package com.example.missingpets.adapterRV

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.missingpets.modelRV.Buscado

class BuscadosAdapter (private val context: Context, private val dataset: List<Buscado>)
    : RecyclerView.Adapter<BuscadosAdapter.BuscadoViewHolder>() {

    class BuscadoViewHolder (private val view: View) : RecyclerView.ViewHolder(view) {
        
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuscadosAdapter.BuscadoViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BuscadosAdapter.BuscadoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}