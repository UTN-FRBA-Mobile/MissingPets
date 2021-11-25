package com.example.missingpets

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.icu.text.RelativeDateTimeFormatter
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.missingpets.databinding.FragmentMaps2Binding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.UiSettings
import java.util.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import android.util.DisplayMetrics
import androidx.navigation.fragment.findNavController
import com.example.missingpets.MainActivity.Companion.prefs


class MapsFragment : Fragment() , OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapLongClickListener {
    private var latitude: String? = null
    private var longitude: String? = null
    private lateinit var mMap: GoogleMap
    var nuevoPost: Boolean = false

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

      override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // obtiene parametros para ver si es localizar posicion  o nuevo post
        getTheArguments()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun getTheArguments() {
        latitude = arguments?.get("latitude") as String?
        longitude = arguments?.get("longitude") as String?

        // Si no recibo como argumentos es porque es un un nuevo post
        if(latitude.isNullOrEmpty()){
            nuevoPost = true;
            // Si es nullo inicializo con Medrano 951 para arrancar
            latitude = "-34.5985579"
            longitude = "-58.4210063"
        }
        if(!nuevoPost) {
            @Suppress("UNCHECKED_CAST")
            Toast
                .makeText(
                    context,
                    "Ubicación actual:  latitud: $latitude, longitud $longitude",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        createMarker()
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
        mMap.setOnMapLongClickListener(this);
        enableLocation()
        val uiSettings: UiSettings = mMap.getUiSettings()
        uiSettings.setAllGesturesEnabled(true)
        uiSettings.isMapToolbarEnabled = true
        uiSettings.isZoomControlsEnabled = true
    }

    /*fun onMapClick(latLng: LatLng?) {
        val format: String = java.lang.String.format(
            Locale.getDefault(),
            "Lat/Lng = (%f,%f)", latLng!!.latitude, latLng.longitude
        )
        Toast.makeText(context, format, Toast.LENGTH_LONG).show()

    }*/

    private fun createMarker() {
        if (!nuevoPost) {
            latitude?.let {
                val positionLatLng = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
                mMap.addMarker(MarkerOptions().position(positionLatLng).title("Mapa"))
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(positionLatLng, 16.0f), 4000, null
                )
            }
        }
    }


    private fun isLocationPermissionGranted(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION
            )
            return true
        }
        return nuevoPost
    }

    @SuppressLint("MissingPermission")
    private fun enableLocation(){
        if(!::mMap.isInitialized) return
        if(isLocationPermissionGranted()){
            mMap.isMyLocationEnabled = true
        }
        else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(requireContext(),"Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
        else {
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                mMap.isMyLocationEnabled = true
            }
            else{
                Toast.makeText(requireContext(),"Activa la localización desde ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        if(!isLocationPermissionGranted()){
            if(!::mMap.isInitialized) return
            mMap.isMyLocationEnabled = false
            Toast.makeText(requireContext(),"Activa la localización desde ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        // si es false seria para que me lleve a la localizacion, seria para post
        Toast.makeText(requireContext(),"Boton pulsado", Toast.LENGTH_SHORT).show()
      //  return !nuevoPost
        return false
        // si le pongo true no me llevaria -seria para detalle.
        // return true
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(requireContext(),"Estás en ${p0.latitude}, ${p0.longitude}", Toast.LENGTH_SHORT).show()
    }

    override fun onMapLongClick(p0: LatLng) {
        if(!nuevoPost){
            return
        }
        // Añadir marker en la posición
        val marker = mMap.addMarker(MarkerOptions().position(p0))

        // Obtener pixeles reales
        val point: Point = mMap.projection.toScreenLocation(p0)

        // Determinar el ancho total de la pantalla
        val display = DisplayMetrics()
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(display)
        val width = display.widthPixels

        val hue: Float

        // ¿La coordenada de pantalla es menor o igual que la mitad del ancho?
        hue = if (point.x <= width / 2) {
            BitmapDescriptorFactory.HUE_YELLOW
        } else {
            BitmapDescriptorFactory.HUE_ORANGE
        }

        // Cambiar color del marker según el grupo
        marker!!.setIcon(BitmapDescriptorFactory.defaultMarker(hue))
        Toast.makeText(requireContext(),"Bien hecho!!! ahora presiona el botón atrás", Toast.LENGTH_LONG).show()
       // findNavController().popBackStack(R.id.newPostFragment, true);
        prefs.latitude = p0.latitude.toFloat()
        prefs.longitude= p0.longitude.toFloat()
    }
}