package com.kotlin.ourmemories.view.place

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.detail.RecomActivity
import com.kotlin.ourmemories.view.place.adapter.SiDo
import com.kotlin.ourmemories.view.place.adapter.SiDoAdapter
import com.kotlin.ourmemories.view.place.presenter.PlaceContract
import com.kotlin.ourmemories.view.place.presenter.PlacePresenter
import kotlinx.android.synthetic.main.fragment_place_list.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * Created by kimmingyu on 2017. 11. 5..
 */
class PlaceListFragment : Fragment(), View.OnClickListener, PlaceContract.View{
    lateinit var adapter: SiDoAdapter

    lateinit var presenter: PlaceContract.Presenter
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_place_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val animator = recycler_view.itemAnimator
        if (animator is DefaultItemAnimator) {
            (animator).supportsChangeAnimations = false
        }

        presenter = PlacePresenter().apply {
            fragment = this@PlaceListFragment
            mView = this@PlaceListFragment
            mContext = context
        }
        presenter.getCityDate()
    }

    override fun updateCityView(siDo:List<SiDo>) {
        adapter = SiDoAdapter(siDo)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter
        adapter.setOnclickListener(this)
    }

    override fun onClick(v: View?) {
        val textView: TextView = v?.findViewById<View>(R.id.list_item_sigungu_name) as TextView
        val name = textView.text.toString()
        activity.toast(name)
        activity.startActivity<RecomActivity>(RecomActivity.EXTRA_SIGUNGU_NAME to name)
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