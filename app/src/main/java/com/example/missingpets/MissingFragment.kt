package com.example.missingpets

import MissingAdapter
import android.os.Bundle
import android.util.Log
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
import com.example.missingpets.network.ApiServices2
import com.example.missingpets.network.MissingPet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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


        val apiInterface = ApiServices2.create().getMissingPets()

        apiInterface.enqueue( object : Callback<List<MissingPet>> {
            override fun onResponse(call: Call<List<MissingPet>>?, response: Response<List<MissingPet>>?) {

                if(response?.body() != null){

                    //val myDataset = MissingDatasource().loadMissingPets() //Datos de la api
                    //Log.d("Missing", "Tamaño de la lista: "+ myDataset.size.toString())

                    //   val myDataset = MissingDatasource().loadMissingPetsLocal() //Datos harckodeados
                    recyclerView = binding.recyclerViewMissingPets
                    recyclerView.adapter = MissingAdapter(response.body()!!,MissingAdapter.OnClickListener {

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

            override fun onFailure(call: Call<List<MissingPet>>, t: Throwable) {

            }


        })

    }

}