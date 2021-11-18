package com.example.missingpets

import MissingAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.missingpets.dataRV.AdoptableDatasource

import com.example.missingpets.dataRV.MissingDatasource
import com.example.missingpets.databinding.FragmentAdoptableBinding
import com.example.missingpets.databinding.FragmentMissingBinding
import com.example.missingpets.models.RepositorioUsuario
import com.example.missingpets.network.MissingPet
import com.example.missingpets.network.recyclerPet

class AdoptableFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentAdoptableBinding? = null
    private val binding get() = _binding!!
    private val repositorioDeUsuario: RepositorioUsuario = RepositorioUsuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdoptableBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val myDataset = AdoptableDatasource().loadAdoptablePets() //Datos hardcodeados

        recyclerView = binding.recyclerViewAdoptablePets
         recyclerView.adapter = MissingAdapter(myDataset, MissingAdapter.OnClickListener {

            if (repositorioDeUsuario.estasLogueado()){
                findNavController().navigate(R.id.action_adoptableFragment_to_detailFragment)
            } else {
                findNavController().navigate(R.id.action_adoptableFragment_to_loginFragment2)
            }


        })
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)



    }


}