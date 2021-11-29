package com.example.missingpets

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.missingpets.MainActivity.Companion.prefs
import com.example.missingpets.databinding.FragmentFilterBinding
import com.example.missingpets.formats.DateFormat
import com.squareup.picasso.Picasso
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener


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

       /* binding.rdEncontrado.setOnClickListener{
            binding.dateDesde.isVisible= false
            binding.dateHasta.isVisible=false
        }

        binding.rbPerdido.setOnClickListener{
            binding.dateDesde.isVisible=true
            binding.dateHasta.isVisible=true
        }*/

        binding.dateDesde.setOnClickListener {
            showdesdeDatePickerDialog() }

        binding.dateHasta.setOnClickListener {
            showhastaDatePickerDialog() }

        binding.imageviewMapa.setOnClickListener {
            val action = R.id.action_filterFragment_to_mapsFragment
            findNavController().navigate(action)
        }

        binding.btnAplicarFiltro.setOnClickListener {
            var action = R.id.action_filterFragment_to_missingFragment2
            if(binding.rdEncontrado.isChecked){
                action = R.id.action_filterFragment_to_adoptableFragment
            }
            val sexo =  if(binding.spnSexoAnimales.selectedItem.toString()=="Sexo") "" else binding.spnSexoAnimales.selectedItem.toString()
            val tipodeanimal = if(binding.spnTipoAnimales.selectedItem.toString()=="Tipo de Animal") "" else binding.spnTipoAnimales.selectedItem.toString()
            val fechadesde = if(binding.dateDesde.text.toString() == "") "" else DateFormat.yyyymmddToddmmyyyy(binding.dateDesde.text.toString())
            val fechahasta = if(binding.dateHasta.text.toString() == "") "" else DateFormat.yyyymmddToddmmyyyy(binding.dateHasta.text.toString())

            val bundle = bundleOf(
                Pair("filtrar", true),
                Pair("tipoMascota", tipodeanimal),
                Pair("sexo", sexo),
                Pair("distanciaMaximaKm", binding.tvCantidadKm.text.toString().toInt()),
                Pair("latitude", binding.tvLatitude.text.toString().toFloat()),
                Pair("longitude", binding.tvLongitude.text.toString().toFloat()),
                Pair("fechadesde", fechadesde),
                Pair("fechahasta", fechahasta))
            findNavController().navigate(action,bundle)
        }

        binding.btnRegresar.setOnClickListener {
            var action = R.id.action_filterFragment_to_missingFragment2
            if(binding.rdEncontrado.isChecked){
                action = R.id.action_filterFragment_to_adoptableFragment
            }
            findNavController().navigate(action)
        }

        var seekBar: SeekBar = requireView()!!.findViewById(R.id.sb_kilometros)
        if(seekBar != null) {
            //seekBar.max(50);
            //seekBar.min(1);
            seekBar.setProgress(5);

            seekBar.setOnSeekBarChangeListener(
                object : OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar,
                        progress: Int, fromUser: Boolean
                    ) {

                        var textviewCantidadKilometros: TextView = requireView()!!.findViewById(R.id.tv_cantidad_km)
                        textviewCantidadKilometros.setText("$progress")
                    }

                    //hace un llamado  cuando se toca la perilla
                    override fun onStartTrackingTouch(seekBar: SeekBar) {}

                    //hace un llamado  cuando se detiene la perilla
                    override fun onStopTrackingTouch(seekBar: SeekBar) {}
                })

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