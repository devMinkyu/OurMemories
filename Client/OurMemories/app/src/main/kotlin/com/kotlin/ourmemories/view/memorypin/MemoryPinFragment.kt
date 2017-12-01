package com.kotlin.ourmemories.view.memorypin

import android.os.Bundle

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.data.source.memory.MemoryRepository
import com.kotlin.ourmemories.view.memorypin.adapter.Item
import com.kotlin.ourmemories.view.memorypin.adapter.MemoryPinAdapter
import com.kotlin.ourmemories.view.memorypin.presenter.MemoryPinContract
import com.kotlin.ourmemories.view.memorypin.presenter.MemoryPinPresenter
import kotlinx.android.synthetic.main.fragment_memorypin.*


/**
 * Created by kimmingyu on 2017. 11. 12..
 */
class MemoryPinFragment : Fragment(), View.OnClickListener, MemoryPinContract.View {
    var adapter: MemoryPinAdapter? = null
    lateinit var presenter: MemoryPinContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_memorypin, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        memoryPinRecyclerView.layoutManager = LinearLayoutManager(context)
        memoryPinRecyclerView.setHasFixedSize(true)

        // 배경 반투명화
        val alpha = memoryPin.background
        alpha.alpha = 200

        presenter = MemoryPinPresenter().apply {
            mView = this@MemoryPinFragment
            fragment = this@MemoryPinFragment
        }

        presenter.memoryData = MemoryRepository(context).apply {
            memoryPinPresenter = presenter as MemoryPinPresenter
        }

        val items: ArrayList<Item> = ArrayList()
        val item = arrayOfNulls<Item>(2)
        item[0] = Item(0, R.color.timeCapsuleButton, "TimeCapsule", resources.getString(R.string.memory_pin_timecapsule_subtitle))
        item[1] = Item(1, R.color.reviewButton, "Review", resources.getString(R.string.memory_pin_review_subtitle))

        (0 until item.size).forEach { i ->
            items += item[i]!!
        }

        adapter = MemoryPinAdapter(context, items)
        memoryPinRecyclerView.adapter = adapter
        adapter?.setOnItemClickListener(this)
    }

    override fun onClick(p0: View?) {
        val id = p0?.findViewById<TextView>(R.id.memoryPinTitle)?.tag
        val classification: Int = id as Int
        presenter.isMemoryCheck(classification)
    }

    // GoogleApiClient 를 등록하기 위해 켜질 떄 등록하고 나갈때 꺼지게 한다
    override fun onStop() {
        if (presenter.mGoogleApiClient != null) {
            presenter.mGoogleApiClient!!.disconnect()
            presenter.mGoogleApiClient = null
        }
        super.onStop()
    }
    override fun onStart() {
        super.onStart()
        if (presenter.mGoogleApiClient == null) {
            presenter.mGoogleApiClient = GoogleApiClient.Builder(context).addApi(LocationServices.API).build()
        }
        presenter.mGoogleApiClient!!.connect()
    }
}
