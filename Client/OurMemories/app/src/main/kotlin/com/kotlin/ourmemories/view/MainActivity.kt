package com.kotlin.ourmemories.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.memory.MemoryFragment
import com.kotlin.ourmemories.view.memorypin.MemoryPinFragment
import com.kotlin.ourmemories.view.memorylist.MemoryListFragment
import com.kotlin.ourmemories.view.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        val CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf"
        var position = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, HomeFragment()).commit()
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.actionHome->{
                    supportFragmentManager.beginTransaction().replace(R.id.mainContainer, HomeFragment()).commit()
                }
                R.id.actionMemory->{
                    supportFragmentManager.beginTransaction().replace(R.id.mainContainer, MemoryFragment()).commit()
                }
                R.id.actionMemoryPin-> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainContainer, MemoryPinFragment()).commit()
                }
                R.id.actionMemoryList-> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainContainer, MemoryListFragment()).commit()
                }
                R.id.actionSetting-> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainContainer, SettingFragment()).commit()
                }
            }
            true
        }

    }

    fun changeFragment(num:Int, fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit()
        when(num){
            0->
                bottomNavigation.selectedItemId = bottomNavigation.menu.findItem(R.id.actionMemoryPin).itemId
            1->
                bottomNavigation.selectedItemId = bottomNavigation.menu.findItem(R.id.actionMemory).itemId
            2->
                bottomNavigation.selectedItemId = bottomNavigation.menu.findItem(R.id.actionMemoryList).itemId
        }

    }

    override fun onResume() {
        super.onResume()
    }
}
