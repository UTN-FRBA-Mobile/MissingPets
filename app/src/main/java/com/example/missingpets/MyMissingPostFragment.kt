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
import com.example.missingpets.adapterRV.MyPostAdapter
import com.example.missingpets.databinding.FragmentMyMissingPostBinding
import com.example.missingpets.databinding.FragmentMyPostsBinding
import com.example.missingpets.models.RepositorioUsuario
import com.example.missingpets.network.ApiServices2
import com.example.missingpets.network.recyclerPet2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyMissingPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyMissingPostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentMyMissingPostBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val repositorioDeUsuario: RepositorioUsuario = RepositorioUsuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyMissingPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val action = arguments?.getString("action").toString().toInt()
        traerMyPost(0,action);
    }

    private fun traerMyPost(idcreator:Int,idMethod:Int) {

        lateinit var apiInterface: Call<List<recyclerPet2>>
        if(idMethod==0){ //Missing
            apiInterface = ApiServices2.create().getMissingPets2()

        }else{//Adoptable
            apiInterface = ApiServices2.create().getFound2()
        }



        apiInterface.enqueue( object : Callback<List<recyclerPet2>> {
            override fun onResponse(call: Call<List<recyclerPet2>>?, response: Response<List<recyclerPet2>>?) {

                if(response?.body() != null){
                    var myPost= response.body()!!.filter{it.idcreator==idcreator};
                    recyclerView = binding.recyclerViewMissingPets
                    recyclerView.adapter = MyPostAdapter(myPost,MyPostAdapter.OnClickListener {

                        if (repositorioDeUsuario.estasLogueado()){
                            val bundle = Bundle()
                            bundle.putInt("id", it.id)
                            //findNavController().navigate(R.id.action_missingFragment_to_detailFragment,bundle)
                        } else {
                            //findNavController().navigate(R.id.action_missingFragment_to_loginFragment2)
                        }
                    })
                    recyclerView.layoutManager= LinearLayoutManager(requireContext())
                    recyclerView.setHasFixedSize(true)
                }

            }

            override fun onFailure(call: Call<List<recyclerPet2>>, t: Throwable) {
            }
        })


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyMissingPostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyMissingPostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}