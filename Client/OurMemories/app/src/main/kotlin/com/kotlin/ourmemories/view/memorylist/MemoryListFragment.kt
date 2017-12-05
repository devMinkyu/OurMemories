package com.kotlin.ourmemories.view.memorylist

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import kotlinx.android.synthetic.main.fragment_memorylist.*

/**
 * Created by kimmingyu on 2017. 11. 5..
 */

class MemoryListFragment : Fragment() , View.OnClickListener {
    private lateinit var spruceAnimator : Animator

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_memorylist, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val img_kor = view.findViewById(R.id.img_btn_kor) as ImageButton
        img_btn_kor.setOnClickListener {
            val intent = Intent(context, MemoryListActivity::class.java)
            intent.putExtra("nationName", "대한민국")
            startActivity(intent)
        }
        img_btn_jpn.setOnClickListener {
            val intent = Intent(context, MemoryListActivity::class.java)
            intent.putExtra("nationName", "일본")
            startActivity(intent)
        }
        img_btn_usa.setOnClickListener {
            val intent = Intent(context, MemoryListActivity::class.java)
            intent.putExtra("nationName", "미국")
            startActivity(intent)
        }


    }


    override fun onStop() {
        super.onStop()
    }

    override fun onClick(v: View?) {

    }
}