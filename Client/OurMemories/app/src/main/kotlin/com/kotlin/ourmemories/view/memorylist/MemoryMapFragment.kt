package com.kotlin.ourmemories.view.memorylist

import android.database.Cursor
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kotlin.ourmemories.DB.DBManagerMemory

/**
 * Created by nyoun_000 on 2017-11-11.
 */
class MemoryMapFragment : SupportMapFragment(), OnMapReadyCallback{
    private lateinit var mMap: GoogleMap

    override fun onMapReady(map: GoogleMap?) {
        mMap = map as GoogleMap



        val kor = LatLng(37.574515, 126.976930)
        mMap.addMarker(MarkerOptions().position(kor).title("Seed nay"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kor))

        //임시 테스트 데이터
        //DBManagerMemory.init(activity)
        var cursor: Cursor = DBManagerMemory.getMemoryAllWithCursor()

        if(cursor.moveToFirst()){
            for(n in 0..cursor.columnCount-1) {
                val spot = LatLng(cursor.getString(2).toDouble(), cursor.getString(3).toDouble());
                mMap.addMarker(MarkerOptions().position(spot).title(cursor.getString(1)))
                cursor.moveToNext()
            }
        }

        //

    }
}