package com.kotlin.ourmemories.view.memorylist

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by nyoun_000 on 2017-11-11.
 */
class MemoryMapFragment : SupportMapFragment(), OnMapReadyCallback{
    private lateinit var mMap: GoogleMap

    override fun onMapReady(map: GoogleMap?) {
        mMap = map as GoogleMap

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Seed nay"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        //임시 테스트 데이터

        //

    }
}