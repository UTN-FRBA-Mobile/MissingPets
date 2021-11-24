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
import com.example.missingpets.databinding.FragmentMissingBinding
import com.example.missingpets.databinding.FragmentMyPostsBinding
import com.example.missingpets.network.ApiServices2
import com.example.missingpets.network.recyclerPet2
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException


class MyPostsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentMyPostsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnMyMissingPost1.setOnClickListener{
            findNavController().navigate(R.id.action_myPostsFragment_to_myMissingPostFragment)
        }

        binding.btnMyMissingPost2.setOnClickListener{
            findNavController().navigate(R.id.action_myPostsFragment_to_myMissingPostFragment)
        }



    }

    private fun traerMising(idcreator:Int) {

        val apiInterface = ApiServices2.create().getMissingPets2()


        apiInterface.enqueue( object : Callback<List<recyclerPet2>> {
            override fun onResponse(call: Call<List<recyclerPet2>>?, response: Response<List<recyclerPet2>>?) {

                if(response?.body() != null){
                    binding.txt2.text= response.body()!!.filter{it.idcreator==idcreator}.toString();
                }

            }

            override fun onFailure(call: Call<List<recyclerPet2>>, t: Throwable) {
            }
        })




    }

    fun traerAdoptable(idcreator:Int){

        val apiInterface = ApiServices2.create().getFound2()

        apiInterface.enqueue( object : Callback<List<recyclerPet2>> {
            override fun onResponse(call: Call<List<recyclerPet2>>?, response: Response<List<recyclerPet2>>?) {

                if(response?.body() != null){
                    binding.txt2.text= response.body()!!.filter{it.idcreator==idcreator}.toString();
                }

            }

            override fun onFailure(call: Call<List<recyclerPet2>>, t: Throwable) {
            }
        })


    }


}