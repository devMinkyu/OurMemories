package com.kotlin.ourmemories.view.place.detail

/**
 * Created by hee on 2017-11-28.
 */
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kotlin.ourmemories.R
import kotlinx.android.synthetic.main.activity_recom.*
import java.io.InputStream
import java.io.InputStreamReader

class RecomActivity : AppCompatActivity() {
    companion object {
        val EXTRA_SIGUNGU_NAME = "sigungu_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sigungu = intent.getStringExtra(EXTRA_SIGUNGU_NAME)
        setContentView(R.layout.activity_recom)
        val data: RecomData? = getDataFromName(sigungu)
        initView(data)
    }

    private fun getDataFromName(selected:String): RecomData?
    {
        val gson: Gson = GsonBuilder().create()
        val inputStream: InputStream = assets.open("recom_data.json")
        val reader: InputStreamReader = InputStreamReader(inputStream)
        val recomData = gson.fromJson(reader, GsonData::class.java)

        for(data in recomData.data)
        {
            if(selected.equals(data.name))
            {
                return data
            }
        }
        return null
    }

    private fun initView(data: RecomData?)
    {
        place_name1.text = data?.storename1
        place_address1.text = data?.storeaddress1
        place_name2.text = data?.storename2
        place_address2.text = data?.storeaddress2
        place_name3.text = data?.storename3
        place_address3.text = data?.storeaddress3

    }
}
