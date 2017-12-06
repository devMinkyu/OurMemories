package com.kotlin.ourmemories.view.setting

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.view.MainActivity
import com.kotlin.ourmemories.view.setting.presenter.SettingContract
import com.kotlin.ourmemories.view.setting.presenter.SettingPresenter
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * Created by kimmingyu on 2017. 11. 5..
 */
class SettingFragment : Fragment() {
    private lateinit var presenter:SettingContract.Presenter
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_setting, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val canaroExtraBold = Typeface.createFromAsset(context.assets, MainActivity.CANARO_EXTRA_BOLD_PATH)
        settingTitleText.typeface = canaroExtraBold

        presenter = SettingPresenter().apply {
            fragment = this@SettingFragment
            mContext = context
        }

        logout.setOnClickListener {
            presenter.logOut()
        }
    }
}