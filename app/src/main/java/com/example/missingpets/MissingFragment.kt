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
import com.example.missingpets.dataRV.MissingDatasource
import com.example.missingpets.databinding.FragmentMissingBinding
import com.example.missingpets.models.RepositorioUsuario

class MissingFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentMissingBinding? = null
    private val binding get() = _binding!!
    private val repositorioDeUsuario: RepositorioUsuario = RepositorioUsuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMissingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myDataset = MissingDatasource().loadMissingPets() //Datos de la api
     //   val myDataset = MissingDatasource().loadMissingPetsLocal() //Datos harckodeados
        recyclerView = binding.recyclerViewMissingPets
        recyclerView.adapter = MissingAdapter(myDataset,MissingAdapter.OnClickListener {

            if (repositorioDeUsuario.estasLogueado()){
                findNavController().navigate(R.id.action_missingFragment_to_detailFragment)
            } else {
                findNavController().navigate(R.id.action_missingFragment_to_loginFragment2)
            }
        })
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }


}