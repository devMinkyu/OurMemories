package com.kotlin.ourmemories.view.memorylist

import android.animation.Animator
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.MainActivity
import kotlinx.android.synthetic.main.fragment_memorylist.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by kimmingyu on 2017. 11. 5..
 */

class MemoryListFragment : Fragment() {
    private lateinit var spruceAnimator : Animator

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_memorylist, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        img_btn_kor.setOnClickListener {
            startActivity<MemoryListActivity>("nationName" to "대한민국")
        }
        img_btn_jpn.setOnClickListener {
            startActivity<MemoryListActivity>("nationName" to "일본")
        }
        img_btn_usa.setOnClickListener {
            startActivity<MemoryListActivity>("nationName" to "미국")
        }

        val canaroExtraBold = Typeface.createFromAsset(context.assets, MainActivity.CANARO_EXTRA_BOLD_PATH)
        memoryListTitleText.typeface = canaroExtraBold

        // 배경 반투명화
        val alpha = img_btn_kor.drawable
        alpha.alpha = 200


    }

    override fun onStop() {
        super.onStop()
    }

}