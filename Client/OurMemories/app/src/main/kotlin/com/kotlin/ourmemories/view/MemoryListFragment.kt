package com.kotlin.ourmemories.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.memorylist.MemoryListActivity
import kotlinx.android.synthetic.main.fragment_memorylist.*

/**
 * Created by kimmingyu on 2017. 11. 5..
 */
class MemoryListFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        return inflater?.inflate(R.layout.fragment_memorylist, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        btn_to_memoryList.setOnClickListener {
            val intent = Intent(context, MemoryListActivity::class.java)
            startActivity(intent)
        }

    }

    //    fun onClickBtn_go_to_list(v:View?){
//        val intent = Intent(activity, MemoryListActivity::class.java)
//        startActivity(intent)
//
//    }
//    btn_to_memoryList.setOnClickListener{
//        val intent = Intent(activity, MemoryListActivity::class.java)
//        startActivity(intent)
//    }

}