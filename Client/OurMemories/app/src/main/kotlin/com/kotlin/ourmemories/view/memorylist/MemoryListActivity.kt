package com.kotlin.ourmemories.view.memorylist

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import com.google.android.gms.maps.model.LatLng
import com.kotlin.ourmemories.DB.DBManagerMemory
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.manager.GlobalApplication.Companion.context
import com.willowtreeapps.spruce.Spruce
import com.willowtreeapps.spruce.animation.DefaultAnimations
import com.willowtreeapps.spruce.sort.DefaultSort
import kotlinx.android.synthetic.main.activity_memory_list.*
import org.jetbrains.anko.toast

class MemoryListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var spruceAnimator : Animator
    init {
        DBManagerMemory.init(this)
    }
    var nationName : String
    init {
        nationName = ""
        DBManagerMemory.init(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_list)

        //Fragment에서 넘겨받은 intent 받는 로직
        val intent : Intent = getIntent()
        nationName = intent.getStringExtra("nationName")


        //엑티비티에 리사이클 뷰 달아주는 로직
        timecapsule_list.layoutManager = LinearLayoutManager(this)
        //애니메이션
        val linearLayoutManager = object : LinearLayoutManager(context) {
            override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
                super.onLayoutChildren(recycler, state)
                initSpruce(timecapsule_list)
            }
        }

        //DBManagerMemory.init(this)
        //DBManagerMemory.defaultAddTimeCapesule()

        //리사이클 뷰에 DB내용 넣어주는 로직
        //애니메이션 셋팅
        timecapsule_list.layoutManager = linearLayoutManager
        val cursor = DBManagerMemory.getMemoriesWithCursor(nationName)
        if(cursor?.count != 0){
            var adapter = TimeCapsuleAdapter(this, cursor!!)
            timecapsule_list.adapter = adapter
        }

    }

    fun isCheckAll(v: View?){
        val rb_all = v?.findViewById<View>(R.id.rb_all) as RadioButton
        if (rb_all.isChecked) {
            toast(rb_all.text.toString())
            val cursor = DBManagerMemory.getMemoriesWithCursor(nationName)
            if(cursor?.count != 0){
                var adapter = TimeCapsuleAdapter(this, cursor!!)
                timecapsule_list.adapter = adapter
            }
        }
    }
    fun isCheckTimecapsule (v: View?) {
        val rb_timecapsule = v?.findViewById<View>(R.id.rb_timecapsule) as RadioButton
        if (rb_timecapsule.isChecked) {
            toast(rb_timecapsule.text.toString())
            val cursor = DBManagerMemory.getMemoryClassificationWithCursor(0)
            if(cursor?.count != 0){
                var adapter = TimeCapsuleAdapter(this, cursor!!)
                timecapsule_list.adapter = adapter
            }
        }
    }
    fun isCheckReview (v: View?) {
        val rb_reView = v?.findViewById<View>(R.id.rb_review) as RadioButton
        if (rb_reView.isChecked) {
            toast(rb_reView.text.toString())
            val cursor = DBManagerMemory.getMemoryClassificationWithCursor(1)
            if(cursor?.count != 0){
                var adapter = TimeCapsuleAdapter(this, cursor!!)
                timecapsule_list.adapter = adapter
            }
        }
    }
    private fun initSpruce(recycleListView : RecyclerView) {
        spruceAnimator = Spruce.SpruceBuilder(recycleListView)
                .sortWith(DefaultSort(100))
                .animateWith(DefaultAnimations.shrinkAnimator(recycleListView, 800),
                        ObjectAnimator.ofFloat(recycleListView, "translationX", -recycleListView.width.toFloat(), 0f).setDuration(800))
                .start()
    }

    override fun onStart() {
        super.onStart()
        //fragment자리에 map fragment달아주는 작업
        val mapFragment = supportFragmentManager.findFragmentById(R.id.memoryMap) as MemoryListMapFragment
        mapFragment.getMapAsync(mapFragment)


    }
    override fun onStop() {
        super.onStop()

        DBManagerMemory.close()
    }

    //리사이클뷰 아이템 클릭 이벤트
    fun onClickTimeCapsual(v: View?){
        val tv_latitude = v?.findViewById<View>(R.id.tv_latitude) as TextView
        val tv_longitude = v?.findViewById<View>(R.id.tv_longitude) as TextView
        //지도 카메라를 해당 아이템 좌표로 옮김.
        var mapFragment = supportFragmentManager.findFragmentById(R.id.memoryMap) as MemoryListMapFragment
        mapFragment.moveToMapCameraPosition(LatLng(tv_latitude.text.toString().toDouble(), tv_longitude.text.toString().toDouble()),
                15.0f)
    }

    override fun onClick(v: View?) {
    }
}
