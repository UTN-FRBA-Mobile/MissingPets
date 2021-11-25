package com.example.missingpets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.missingpets.databinding.FragmentMyAdpotablePostBinding
import com.example.missingpets.databinding.FragmentMyDetailBinding
import com.example.missingpets.formats.DateFormat
import com.example.missingpets.network.ApiServices2
import com.example.missingpets.network.Mascota
import com.example.missingpets.viewModels.DetailViewModel
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentMyDetailBinding? = null
    private val binding get() = _binding!!
    private var idmascota: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            arguments?.let {
                idmascota = it.getInt("id")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargarPost();
        binding.btnBorrar.setOnClickListener{

            borrarMascota(idmascota)

        }


    }

    fun cargarPost(){
        val apiInterface = ApiServices2.create().getLostById(idmascota)

        apiInterface.enqueue(object : Callback<List<Mascota>> {
            override fun onResponse(call: Call<List<Mascota>>?, response: Response<List<Mascota>>?) {

                binding.txtDescription.text= response?.body()?.first()?.description.toString()
                val photopath = resources.getString(com.example.missingpets.R.string.images_root_path) + response?.body()?.first()?.photopath.toString()
                Picasso.get().load(photopath).into(binding.ivImage)

            }

            override fun onFailure(call: Call<List<Mascota>>, t: Throwable) {
                Log.e("Error:::", "Error " + t!!.message)
            }
        })


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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}