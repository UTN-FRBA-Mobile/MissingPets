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
import androidx.fragment.app.FragmentActivity
import com.example.missingpets.dataRV.MissingDatasource
import com.example.missingpets.databinding.FragmentPostMissingBinding
import com.example.missingpets.network.Mascota
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

        //Spinner del tipo del animal
        val tipoDeAnimal = resources.getStringArray(R.array.animals)
        val spinnerAnimales = binding.tvEtiquetaTipoAnimal
        // initialize an array adapter for spinner
        val adapterAnimales:ArrayAdapter<String> = initializeSpinnerAdapter(tipoDeAnimal, spinnerAnimales)
        // finally, data bind spinner with adapter
        spinnerAnimales.adapter = adapterAnimales

        // Spinner del sexo del animal
        val sexoDelAnimal = resources.getStringArray(R.array.animals_sex)
        val spinnerSexo = binding.tvEtiquetaSexo
        // initialize an array adapter for spinner
        val adapter:ArrayAdapter<String> = initializeSpinnerAdapter(sexoDelAnimal, spinnerSexo)
        // finally, data bind spinner with adapter
        spinnerSexo.adapter = adapter



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

        binding.etDate.setOnClickListener { showDatePickerDialog() }

    }


    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val realMonth = month + 1
        binding.etDate.setText("$day/$realMonth/$year")
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

    private fun initializeSpinnerAdapter(items: Array<String>, spinner: Spinner): ArrayAdapter<String> {

        return object : ArrayAdapter<String>( this.requireContext(),
            android.R.layout.simple_spinner_dropdown_item, items) {

            override fun getDropDownView( position: Int, convertView: View?, parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                // set item text bold
                view.setTypeface(view.typeface, Typeface.BOLD)

                // set selected item style
                if (position == spinner.selectedItemPosition && position != 0) {
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                // make hint item color gray
                if (position == 0) {
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
    }
}