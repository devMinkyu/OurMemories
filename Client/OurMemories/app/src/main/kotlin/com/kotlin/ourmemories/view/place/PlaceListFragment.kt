package com.kotlin.ourmemories.view.place

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.MainActivity
import com.kotlin.ourmemories.view.recom.RecomActivity
import com.kotlin.ourmemories.view.place.adapter.data.SiDo
import com.kotlin.ourmemories.view.place.adapter.SiDoAdapter
import com.kotlin.ourmemories.view.place.presenter.PlaceContract
import com.kotlin.ourmemories.view.place.presenter.PlacePresenter
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import kotlinx.android.synthetic.main.fragment_place_list.*
import org.jetbrains.anko.startActivity


/**
 * Created by kimmingyu on 2017. 11. 5..
 */
class PlaceListFragment : Fragment(), View.OnClickListener, PlaceContract.View{
    lateinit var adapter: SiDoAdapter
    lateinit var presenter: PlaceContract.Presenter
    var openGroup:ExpandableGroup<*>? = null

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

        adapter.setOnGroupExpandCollapseListener(object : GroupExpandCollapseListener{
            override fun onGroupCollapsed(group: ExpandableGroup<*>?) {}
            override fun onGroupExpanded(group: ExpandableGroup<*>?) { // 열려있는거
                openGroup = if(openGroup == null){
                    group
                }else{
                    adapter.toggleGroup(openGroup)
                    group
                }
            }
        })

    }

    override fun updateCityView(siDo:List<SiDo>) {
        adapter = SiDoAdapter(siDo)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter
        adapter.setOnclickListener(this)
    }

    override fun onClick(v: View?) {
        val siGunGu: TextView = v?.findViewById<View>(R.id.list_item_sigungu_name) as TextView
        val titName = openGroup?.title.toString()
        val subName = siGunGu.text.toString()
        activity.startActivity<RecomActivity>(RecomActivity.EXTRA_SIGUNGU_NAME to subName, RecomActivity.EXTRA_SIDO_NAME to titName)
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