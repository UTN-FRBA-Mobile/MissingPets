package com.example.missingpets

import AdoptableAdapter
import MissingAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.missingpets.dataRV.AdoptableDatasource
import com.example.missingpets.dataRV.MissingDatasource

import com.example.missingpets.databinding.FragmentAdoptableBinding
import com.example.missingpets.models.RepositorioUsuario
import com.example.missingpets.network.ApiServices2
import com.example.missingpets.network.MissingPet
import com.example.missingpets.network.recyclerPet
import com.example.missingpets.viewModels.UserProfileViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdoptableFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentAdoptableBinding? = null
    private val binding get() = _binding!!
    private val repositorioDeUsuario: RepositorioUsuario = RepositorioUsuario
    private val user: UserProfileViewModel by activityViewModels()

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


        val apiInterface = ApiServices2.create().getMissingPets()

        apiInterface.enqueue( object : Callback<List<recyclerPet>> {
            override fun onResponse(call: Call<List<recyclerPet>>?, response: Response<List<recyclerPet>>?) {

                if(response?.body() != null){
                    var adoptableAnimals = response.body()!!.filter { it.estado=="encontrado" }

                    recyclerView = binding.recyclerViewAdoptablePets
                    recyclerView.adapter = AdoptableAdapter(adoptableAnimals,AdoptableAdapter.OnClickListener {

                        if (user.id>=0){//repositorioDeUsuario.estasLogueado()
                            findNavController().navigate(R.id.action_adoptableFragment_to_detailFragment)
                        } else {
                            findNavController().navigate(R.id.action_adoptableFragment_to_loginFragment2)
                        }
                    })
                    recyclerView.layoutManager= LinearLayoutManager(requireContext())
                    recyclerView.setHasFixedSize(true)
                }

            }

            override fun onFailure(call: Call<List<recyclerPet>>, t: Throwable) {
            }
        })
    }
}