package com.example.missingpets

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.missingpets.MainActivity.Companion.prefs
import com.example.missingpets.databinding.FragmentFilterBinding
import com.example.missingpets.databinding.FragmentNewPostBinding


class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // en el mapa las marca - al volver en onStart las asigna
        prefs.latitude = 0f
        prefs.longitude= 0f

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFilterBinding.inflate(inflater, container, false)

        //Spinner del tipo del animal
        val tipoDeAnimal = resources.getStringArray(R.array.animals)
        val spinnerAnimales = binding.spnTipoAnimales

        val adapterAnimales: ArrayAdapter<String> = initializeSpinnerAdapter(tipoDeAnimal, spinnerAnimales)
        spinnerAnimales.adapter = adapterAnimales

        // Spinner del sexo del animal
        val sexoDelAnimal = resources.getStringArray(R.array.animals_sex)
        val spinnerSexo = binding.spnSexoAnimales
        val adapter: ArrayAdapter<String> = initializeSpinnerAdapter(sexoDelAnimal, spinnerSexo)
        spinnerSexo.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.dateDesde.setOnClickListener {
            showdesdeDatePickerDialog() }

        binding.dateHasta.setOnClickListener {
            showhastaDatePickerDialog() }

        binding.imageviewMapa.setOnClickListener {
            val action = R.id.action_filterFragment_to_mapsFragment
            findNavController().navigate(action)
        }

        binding.btnAplicarFiltro.setOnClickListener {
            val action = R.id.action_filterFragment_to_missingFragment2

            val bundle = bundleOf(
            Pair("filtrar", "true"),
            Pair("tipoMascota", binding.spnTipoAnimales.selectedItem.toString()),
            Pair("sexo", binding.spnSexoAnimales.selectedItem.toString()),
            Pair("distanciaMaximaKm", binding.tvCantidadKm.text.toString().toInt()),
            Pair("latitude", binding.tvLatitude.text.toString().toFloat()),
            Pair("longitude", binding.tvLongitude.text.toString().toFloat()))
            findNavController().navigate(action,bundle)
        }

        binding.btnRegresar.setOnClickListener {
            val action = R.id.action_filterFragment_to_missingFragment2
            findNavController().navigate(action)



        /*    when(prefs.cualRecycler)
            {
                "L" -> findNavController().navigate(R.id.action_filterFragment_to_missingFragment)
                "F" -> findNavController().navigate(R.id.action_filterFragment_to_missingFragment)
            }*/

        }
    }

    override fun onStart() {
        super.onStart()
        binding.tvLatitude.text = MainActivity.prefs.latitude.toString()
        binding.tvLongitude.text = MainActivity.prefs.longitude.toString()
    }

    private fun showdesdeDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> ondesdeDateSelected(day, month, year) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun ondesdeDateSelected(day: Int, month: Int, year: Int) {
        val realMonth = month + 1
        binding.dateDesde.setText("$day-$realMonth-$year")
    }

    private fun showhastaDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onhastaDateSelected(day, month, year) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onhastaDateSelected(day: Int, month: Int, year: Int) {
        val realMonth = month + 1
        binding.dateHasta.setText("$day-$realMonth-$year")
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