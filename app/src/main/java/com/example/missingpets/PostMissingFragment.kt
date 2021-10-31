package com.example.missingpets

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.missingpets.databinding.FragmentDetailBinding
import com.example.missingpets.databinding.FragmentPostMissingBinding
import com.example.missingpets.viewModels.CamaraViewModel
import com.example.missingpets.viewModels.DetailViewModel
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostMissingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostMissingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    // private var btnCamara: Button = null*

    private lateinit var detailViewModel: CamaraViewModel
    private var _binding: FragmentPostMissingBinding? = null
    private val binding get() = _binding!!



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
        _binding = FragmentPostMissingBinding.inflate(inflater, container, false)
        return binding.root
    //inflater.inflate(R.layout.fragment_post_missing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        detailViewModel = CamaraViewModel()

        binding.btnCamara.setOnClickListener {
            abrirCamara(view)
        }
    }

    // Abrir la cámara o subir desde la galeria
    private fun abrirCamara(view: View){
        try{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (activity?.let { intent.resolveActivity(it.packageManager) } != null){
                startActivity(intent)
            }

        }
        catch(e: Exception){
            Snackbar.make(view, "No se pudo abrir cámara", Snackbar.LENGTH_LONG).show();
        }
    }

    fun onActivityResult(data: Intent) {

        var extra: Bundle? = data.extras
        var imgbitmap: Bitmap = extra?.get("data") as Bitmap
        binding.imageView.setImageBitmap(imgbitmap)

    }
}