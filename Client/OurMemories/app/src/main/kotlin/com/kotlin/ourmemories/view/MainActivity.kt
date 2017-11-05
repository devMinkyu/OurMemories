package com.kotlin.ourmemories.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kotlin.ourmemories.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.actionHome->{
                    supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
                }
                R.id.actionGift->{
                    supportFragmentManager.beginTransaction().replace(R.id.container, GiftFragment()).commit()
                }
                R.id.actionReorder-> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, ReorderFragment()).commit()
                }
                R.id.actionSetting-> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, SettingFragment()).commit()
                }
            }
            true
        }

    }
}
