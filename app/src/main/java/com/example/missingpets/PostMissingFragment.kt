package com.example.missingpets

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.activity.result.contract.ActivityResultContracts
import com.example.missingpets.databinding.FragmentPostMissingBinding
import com.google.android.material.snackbar.Snackbar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [PostMissingFragment. newInstance] factory method to
 * create an instance of this fragment.
 */
class PostMissingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    // private var btnCamara: Button = null*

    private var _binding: FragmentPostMissingBinding? = null
    private val binding get() = _binding!!


    private var resultLauncherCamara = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val data: Intent? = result.data
            val extras = data?.extras
            val imgBitmap = extras!!["data"] as Bitmap?
            binding.ivMascota.setImageBitmap(imgBitmap)

        }
    }
    private var resultLauncherGaleria = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val picture = result.data?.data
            binding.ivMascota.setImageURI(picture)
        }
    }


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
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPostMissingBinding.inflate(inflater, container, false)

        var items = resources.getStringArray(R.array.animals_sex)
        var spinner = binding.tvEtiquetaSexo
        // initialize an array adapter for spinner
        val adapter:ArrayAdapter<String> = object: ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            items
        ){
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view:TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                // set item text bold
                view.setTypeface(view.typeface, Typeface.BOLD)

                // set selected item style
                if (position == spinner.selectedItemPosition && position !=0 ){
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                // make hint item color gray
                if(position == 0){
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }
        }

        // finally, data bind spinner with adapter
        spinner.adapter = adapter

        return binding.root
        //inflater.inflate(R.layout.fragment_post_missing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTomarFoto.setOnClickListener {
            abrirCamara(view)
        }
        binding.btnSubirFoto.setOnClickListener {
            abrirGaleria(view)
        }
    }

    private fun abrirGaleria(view: View) {
        try {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            //startActivity(intent)
            intent.type = "image/*"
            resultLauncherGaleria.launch(intent)

        } catch (e: Exception) {
            Snackbar.make(view, "No se pudo abrir la galeria", Snackbar.LENGTH_LONG).show()
        }
    }

    // Abrir la cámara o subir desde la galeria
    private fun abrirCamara(view: View) {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (activity?.let { intent.resolveActivity(it.packageManager) } != null) {
                //startActivity(intent)
                resultLauncherCamara.launch(intent)
            } else {
                Toast.makeText(this.context, "No se encontró cámara", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Snackbar.make(view, "No se pudo abrir cámara", Snackbar.LENGTH_LONG).show()
        }
    }

}