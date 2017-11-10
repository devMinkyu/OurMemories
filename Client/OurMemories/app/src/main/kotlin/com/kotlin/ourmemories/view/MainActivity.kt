package com.kotlin.ourmemories.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.memory.MemoryFragment
import com.kotlin.ourmemories.view.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        val CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.actionHome->{
                    supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
                }
                R.id.actionMemory->{
                    supportFragmentManager.beginTransaction().replace(R.id.container, MemoryFragment()).commit()
                }
                R.id.actionMemoryList-> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, MemoryListFragment()).commit()
                }
                R.id.actionSetting-> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, SettingFragment()).commit()
                }
            }
            true
        }

    }
}
