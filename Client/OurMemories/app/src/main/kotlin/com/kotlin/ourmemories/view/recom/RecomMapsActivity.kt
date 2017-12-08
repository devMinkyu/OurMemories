package com.kotlin.ourmemories.view.recom

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kotlin.ourmemories.R
import java.util.*

class RecomMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var address : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recom_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        if(intent.getStringExtra("address").toString().isNotEmpty()) {
            //toast(intent.getStringExtra("address").toString())
            address = intent.getStringExtra("address").toString()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val geo = Geocoder(this, Locale.KOREAN)
        val addr : List<Address> = geo.getFromLocationName(address,2)
        val mLatLng = LatLng(addr[0].latitude, addr[0].longitude)
        mMap.addMarker(MarkerOptions().position(mLatLng).title(address))

        var cameraPostion : CameraPosition.Builder = CameraPosition.Builder()
        cameraPostion.target(mLatLng)
        cameraPostion.zoom(15.0f)
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPostion.build()))
    }
}
