package com.kotlin.ourmemories.view.place

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.place.AddressBook.makeAddress
import com.kotlin.ourmemories.view.place.detail.RecomActivity
import com.kotlin.ourmemories.view.place.expand.SiDoAdapter
import kotlinx.android.synthetic.main.fragment_place_list.*
import java.io.InputStreamReader


/**
 * Created by kimmingyu on 2017. 11. 5..
 */
class PlaceListFragment : Fragment(), View.OnClickListener{
    lateinit var adapter: SiDoAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_place_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)

        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        val animator = recycler_view.getItemAnimator()
        if (animator is DefaultItemAnimator) {
            (animator as DefaultItemAnimator).setSupportsChangeAnimations(false)
        }
        val inputStream = InputStreamReader(activity.assets.open("korea_administrative_district"))
        val admin: AdministrativeDistrict = Gson().fromJson(inputStream, AdministrativeDistrict::class.java)

        Log.d("hoho", admin.data[0])
        adapter = SiDoAdapter(makeAddress())
        recycler_view.setLayoutManager(layoutManager)
        recycler_view.setAdapter(adapter)
        adapter.setOnclickListener(this)
    }

    override fun onClick(v: View?) {
        val textView: TextView = v?.findViewById(R.id.list_item_sigungu_name) as TextView
        val name = textView.text.toString()

        val intent = Intent(context, RecomActivity::class.java)
        intent.putExtra(RecomActivity.EXTRA_SIGUNGU_NAME, name);
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        adapter.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        adapter.onRestoreInstanceState(savedInstanceState)
    }


}