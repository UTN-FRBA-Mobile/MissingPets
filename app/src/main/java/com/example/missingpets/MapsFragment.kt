package com.example.missingpets

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.GoogleMap.CancelableCallback
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MapsFragment : Fragment(), OnInfoWindowClickListener, OnMapReadyCallback {
    private var latitude: String? = null
    private var longitude: String? = null

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
      //  val sydney = LatLng(-34.0, 151.0)
        val sydney = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
        googleMap.addMarker(MarkerOptions().position(sydney).title("Mascota"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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

        getTheArguments()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun getTheArguments() {
        latitude = arguments?.get("latitude") as String?
        longitude = arguments?.get("longitude") as String?
        @Suppress("UNCHECKED_CAST")
        Toast
            .makeText(context, "latitude: $latitude, longitud $longitude", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        latitude?.let {
            val positionLatLng =
                LatLng(latitude!!.toDouble(), longitude!!.toDouble())
            val marker =
                googleMap.addMarker(MarkerOptions().position(positionLatLng).title("Mapa"))
            // marker.tag = 1
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(positionLatLng, 10.0f),
                object : GoogleMap.CancelableCallback {
                    override fun onCancel() = Unit

                    override fun onFinish() {
                        marker!!.showInfoWindow()
                    }
                }
            )
        }
    }

    override fun onInfoWindowClick(p0: Marker) {


    }


}