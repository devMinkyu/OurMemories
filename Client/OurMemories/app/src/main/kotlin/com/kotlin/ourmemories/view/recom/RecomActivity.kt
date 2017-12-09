package com.kotlin.ourmemories.view.recom

/**
 * Created by hee on 2017-11-28.
 */
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.kotlin.ourmemories.R
import com.kotlin.ourmemories.data.jsondata.ReComMemoryResult
import com.kotlin.ourmemories.data.source.recom.RecomRepository
import com.kotlin.ourmemories.view.MainActivity
import com.kotlin.ourmemories.view.recom.adapter.RecomAdapter
import com.kotlin.ourmemories.view.recom.presenter.RecomContract
import com.kotlin.ourmemories.view.recom.presenter.RecomPresenter
import kotlinx.android.synthetic.main.activity_recom.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class RecomActivity : AppCompatActivity(),RecomContract.View , View.OnClickListener{
    companion object {

        val EXTRA_SIGUNGU_NAME = "sigungu_name"

        val EXTRA_SIDO_NAME = "sido_name"
    }
    lateinit var presenter:RecomContract.Presenter
    lateinit var adapter:RecomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recom)

        val sigungu = intent.getStringExtra(EXTRA_SIGUNGU_NAME)
        val sido = intent.getStringExtra(EXTRA_SIDO_NAME)


        val canaroExtraBold = Typeface.createFromAsset(this.assets, MainActivity.CANARO_EXTRA_BOLD_PATH)
        reComToolbarTitleText.typeface = canaroExtraBold

        presenter = RecomPresenter().apply {
            mView = this@RecomActivity
            activity = this@RecomActivity
            memoryData = RecomRepository()
        }

        presenter.getReview(sido,sigungu)

        reComBack.setOnClickListener {
            finish()
        }
        reComList.layoutManager = LinearLayoutManager(this)


    }
    override fun updateView(items: ArrayList<ReComMemoryResult>) {
        adapter = RecomAdapter(applicationContext, items, this)
        reComList.adapter = adapter
        adapter.setOnItemClickListener(this)
    }

    override fun onClick(view: View?) {
        val address = view?.findViewById<View>(R.id.reComAddress) as TextView
        toast(address.text.toString())
        startActivity<RecomMapsActivity>("address" to address.text.toString())
    }

    fun showDialog() { loading_recom.visibility = View.VISIBLE }

    fun hideDialog() {
        loading_recom.visibility = View.INVISIBLE
    }
}
