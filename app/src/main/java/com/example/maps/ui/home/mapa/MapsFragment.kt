package com.example.maps.ui.home.mapa

import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.maps.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory


import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var lastLocation: Location

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun placeMarker(location: LatLng) {
        val casinha = MarkerOptions().position(LatLng(-27.099601, -48.906891))
        val casinha1 = MarkerOptions().position(LatLng(-27.1597896, -48.5758767))

        casinha1.icon(
            BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(resources,
                    R.mipmap.ic_launcher
                )
            )
        )
            .title("casinha1")
            .snippet("Rod. Antônio Heil, 250 - Centro 1,")
            .snippet("Brusque - SC - 88353-100")
        mMap.addMarker(casinha1)

        casinha.icon(
            BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(resources,
                    R.mipmap.ic_launcher
                )
            )
        )
            .title("casinha")
            .snippet("Rod. Antônio Heil, 250 - Centro 1,")
            .snippet("Brusque - SC - 88353-100")
        mMap.addMarker(casinha)
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->

            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarker(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 10f))
            }
        }
    }
}