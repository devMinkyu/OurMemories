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
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true


        var nationName = activity.intent.extras.getString("nationName")
        //context.toast(nationName)
        if(nationName == "대한민국"){
            val kor = LatLng(37.574515, 126.976930)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(kor))
        } else if (nationName == "일본"){
            val jpn = LatLng(35.709261, 139.731070)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(jpn))
        } else if (nationName == "미국"){
            val usa = LatLng(38.906414, -77.040912)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(usa))
        }



        //임시 테스트 데이터
        //DBManagerMemory.init(activity)
        var cursor: Cursor = DBManagerMemory.getMemoriesWithCursor(nationName)

        if(cursor.moveToFirst()){
            for(n in 0..cursor.columnCount-1) {
                val spot = LatLng(cursor.getString(2).toDouble(), cursor.getString(3).toDouble())
                mMap.addMarker(MarkerOptions().position(spot).title(cursor.getString(1)))
                cursor.moveToNext()
            }
        }

        //

    }
}