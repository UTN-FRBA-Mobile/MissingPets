package com.example.missingpets

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
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


class MapsFragment : Fragment() , OnMapReadyCallback { //, OnInfoWindowClickListener, OnMapReadyCallback {
    private var latitude: String? = null
    private var longitude: String? = null

    private lateinit var marcadorActual: Marker
    private val GPS_REQUEST_CODE: Int = 1
    private val MY_PERMISSION_REQUEST_GPS: Int = 99

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMaps2Binding

    // VA A SERVIR PARA ACCEDER A LA INFORMACION DEL GPS
    lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var locationCallback: LocationCallback
    lateinit var lr: LocationRequest

    var gpsIniciado: Boolean = false
    var nuevoPost: Boolean = false

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



        // define un intervalo de actualización
        lr = createLocationRequest()

        if(nuevoPost) {
            // OBTENER LA UBICACION PERIODICAMENTE ASI CUANDO ENCIENDO EL GPS ME RASTREA LA UBICACION
            // ACTUAL SI VOY A HACER UN NUEVO POST O HACER UN CAMBIO DE LOCALIZACION
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    locationResult ?: return
                    for (location in locationResult.locations) {
                        if (latitude.isNullOrEmpty()) {
                            actualizarMarcador(location.latitude, location.longitude)
                            // si era nuevo post asigno la ubicacion aactual a latitude y longitude
                            retenerLocacionNuevaSiEsPost(location.latitude, location.longitude)
                        }
                    }
                }
            }
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun retenerLocacionNuevaSiEsPost(lat: Double, long: Double){
        // si por parametros no viene ninguna locacion, tomo la actual como punto de partida
        // para los nuevos post.
        if(nuevoPost) {
            latitude = lat.toString()
            longitude = long.toString()
        }
    }

    private fun createLocationRequest(): LocationRequest {
        val locationRequest: LocationRequest? = LocationRequest.create()?.apply{
            interval = 2000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        return locationRequest!!
    }

    // por si minimizo la aplicacion pero reingreso
    override fun onResume() {
        super.onResume()
        if(gpsIniciado){
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        if(gpsIniciado){
            stopLocationUpdates()
        }
    }

    private fun getTheArguments() {
        latitude = arguments?.get("latitude") as String?
        longitude = arguments?.get("longitude") as String?

        // Simulo Medrano 951 para probar
        latitude = "-34.5985579"
        longitude = "-58.4210063"
        ///

        // Simulo no hay location, entonces busca la ubicacion actual y seria un nuevo post
        // latitude =null
        // longitude=null
        if(latitude.isNullOrEmpty()){
            nuevoPost = true;
        }
        @Suppress("UNCHECKED_CAST")
        Toast
            .makeText(context, "Nuevo-Post: $nuevoPost, latitud: $latitude, longitud $longitude", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if(nuevoPost){

            // aquí se me cuelga cuando voy a encender el gps para luego mostrar la ubicacion
            // activarGps()
            //
            mostrarUbicacionActual()
        }
        else {
            latitude?.let {
                val positionLatLng =
                    LatLng(latitude!!.toDouble(), longitude!!.toDouble())
                val marker =
                    mMap.addMarker(MarkerOptions().position(positionLatLng).title("Mapa"))
                // marker.tag = 1
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(positionLatLng, 16.0f),
                    object : GoogleMap.CancelableCallback {
                        override fun onCancel() = Unit

                        override fun onFinish() {
                            marker!!.showInfoWindow()
                        }
                    }
                )
                if(marker!=null) {
                    marcadorActual = marker
                }
                solicitarPermisosGps()
            }
        }
    }

    private fun solicitarPermisosGps() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this.requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    mostrarMensaje("Necesario para el uso de la aplicación con el mapa")
                }
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSION_REQUEST_GPS
                )

            }else {
                // permiso concedido
                if( !gpsEncendido()) {
                    mostrarDialogoActivarGPS()
                } else {
                     activarGps()
                    // mostrarUbicacionActual()
                }
            }
        } else {
            // permiso concedido
            if( !gpsEncendido()) {
                mostrarDialogoActivarGPS()
            } else {
                 activarGps()
                // mostrarUbicacionActual()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            MY_PERMISSION_REQUEST_GPS -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // permiso concedido
                    if( !gpsEncendido()) {
                        mostrarDialogoActivarGPS()
                    } else {
                        activarGps()
                        // mostrarUbicacionActual()
                    }
                    mostrarMensaje("Permiso concedido")
                }
                else {
                    mostrarMensaje("PERMISO REQUERIDO")
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSION_REQUEST_GPS)
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun activarGps(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if(it != null) {
                if(nuevoPost) {
                    actualizarMarcador(it.latitude, it.longitude)
                }
            }
        }
        startLocationUpdates()
        gpsIniciado = true
    }

    @SuppressLint("MissingPermission")
    private fun mostrarUbicacionActual() {
        mMap.isMyLocationEnabled = true;
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }

    fun gpsEncendido(): Boolean {
        val manager : LocationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun mostrarDialogoActivarGPS() {
        var alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(this.requireContext())
            builder.apply {
                setTitle("Activar GPS")
                setMessage("Es necesario que el gps este activado")
                setPositiveButton("aceptar",
                    DialogInterface.OnClickListener{ dialog, id->
                        startActivityForResult(
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                            GPS_REQUEST_CODE
                        )
                    }
                )
                setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                        mostrarDialogoActivarGPS()
                    }
                )
            }
            builder.create()
        }
        alertDialog.show()
    }


    private fun actualizarMarcador(latitude: Double, longitude: Double) {
        var coordenadas = LatLng(latitude,longitude)
        if(!marcadorActual.isVisible){
            marcadorActual.isVisible = true
        }
        marcadorActual.position = coordenadas
        marcadorActual.title = "Lat: $latitude, Lng: $longitude"

        //  marcadorActual.position = LatLng(latitude,longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marcadorActual.position))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f))

        mostrarMensaje("Save New Post Position: $latitude , $longitude")
       // dbRef.child("latitude").setValue(latitude)
       // dbRef.child("longitude").setValue(longitude)

        // Use the Kotlin extension in the fragment-ktx artifact
        setFragmentResult("requestLatitude", bundleOf("bundleLatitude" to latitude))
        setFragmentResult("requestLongitude", bundleOf("bundleLongitude" to longitude))
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(){
        if(nuevoPost) {
            fusedLocationClient.requestLocationUpdates(lr, locationCallback, null)
        }
    }

    fun stopLocationUpdates(){
        if(nuevoPost) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            // dbRef.removeValue()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GPS_REQUEST_CODE && gpsEncendido()) {
            activarGps()
            // mostrarUbicacionActual()
        }
        else {
            mostrarDialogoActivarGPS()
        }
    }



}