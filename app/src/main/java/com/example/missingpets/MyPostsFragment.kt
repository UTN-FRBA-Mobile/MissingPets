package com.example.missingpets


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.missingpets.databinding.FragmentMyPostsBinding
import com.example.missingpets.network.ApiServices2
import com.example.missingpets.network.Mascota
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
            val bundle = bundleOf("action" to "0")
            findNavController().navigate(R.id.action_myPostsFragment_to_myMissingPostFragment,bundle)
        }

        binding.btnMyMissingPost2.setOnClickListener{
            val bundle = bundleOf("action" to "1")
            findNavController().navigate(R.id.action_myPostsFragment_to_myMissingPostFragment,bundle)
        }

    }


    fun borrarMascota(idMascota: Int) {
        val apiInterface0 = ApiServices2.create().deleteLost("DELETE", idMascota)

        apiInterface0!!.enqueue(object : Callback<ResponseBody?> {

            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    Log.d("SUCCESS DELETE  MASCOTA - ID " + idMascota, response.body()!!.toString())


                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.e("Error:::", "Error " + t!!.message)
            }
        })
    }
}