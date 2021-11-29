package com.example.missingpets

import MissingAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.missingpets.databinding.FragmentAdoptableBinding
import com.example.missingpets.filter.MascotaListFilter
import com.example.missingpets.network.ApiServices2
import com.example.missingpets.network.Mascota
import com.example.missingpets.viewModels.UserProfileViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdoptableFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentAdoptableBinding? = null
    private val binding get() = _binding!!
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

        val filtrar = arguments?.getBoolean("filtrar")?: false

        val tipoMascota = arguments?.getString("tipoMascota")
        val sexo = arguments?.getString("sexo")
        val distanciaMaximaKm = arguments?.getInt("distanciaMaximaKm")?:0
        val latitude = arguments?.getFloat("latitude")?:0f
        val longitude = arguments?.getFloat("longitude")?:0f

        val apiInterface = ApiServices2.create().getMissingPetsFilter()

        apiInterface.enqueue( object : Callback<List<Mascota>> {
            override fun onResponse(call: Call<List<Mascota>>?, response: Response<List<Mascota>>?) {

                if (response?.body() != null) {
                    var missing = response.body()!!.filter { it.estado == "encontrado" }

                    var missingAnimals: List<Mascota> = ArrayList()
                    if (filtrar && missing.count() > 0) {
                        missingAnimals = MascotaListFilter.filter(
                            missing.toList()
                                .toMutableList(),
                            tipoMascota,
                            sexo,
                            distanciaMaximaKm,
                            latitude,
                            longitude,
                            "01/01/1900",
                            "01/01/2022"
                        )
                        MainActivity.prefs.inicializar()
                    } else {
                        missingAnimals = missing
                    }

                    recyclerView = binding.recyclerViewAdoptablePets
                    recyclerView.adapter =
                        MissingAdapter(missingAnimals, MissingAdapter.OnClickListener {

                            if (user.id >= 0) { // repositorioDeUsuario.estasLogueado()
                                val bundle = Bundle()
                                bundle.putInt("id", it.id!!)
                                findNavController().navigate(
                                    R.id.action_adoptableFragment_to_detailFragment,
                                    bundle
                                )
                            } else {
                                findNavController().navigate(R.id.action_adoptableFragment_to_loginFragment2)
                            }
                        })
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.setHasFixedSize(true)
                }

            }

            override fun onFailure(call: Call<List<Mascota>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}