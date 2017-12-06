package com.kotlin.ourmemories.view.memory

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by nyoun_000 on 2017-11-11.
 */
class MemoryMapFragment : SupportMapFragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onMapReady(map: GoogleMap?) {
        mMap = map as GoogleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
    }

    fun moveToPositionAndAddMaker(lat: Double, lon: Double){
        val latLng = LatLng(lat,lon)
        var cameraPostion : CameraPosition.Builder = CameraPosition.Builder()
        cameraPostion.target(latLng)
        cameraPostion.zoom(15.0f)
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPostion.build()))
        mMap.addMarker(MarkerOptions().position(latLng))
    }
}