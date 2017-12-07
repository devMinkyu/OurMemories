package com.kotlin.ourmemories.unit

import android.app.Activity
import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView
import com.kotlin.ourmemories.DB.DBManagerMemory

/**
 * Created by kimmingyu on 2017. 11. 23..
 */
// 리뷰나 타임캡슐의 내용을 채웠는지 검사하는 부분
class InputVaildation(context:Context) {
    val mContext = context
    fun isInputFilled(message: String, textInputEditText: TextInputEditText, textInputLayout: TextInputLayout): Boolean {
        if(textInputEditText.text.trim().isEmpty()){
            textInputLayout.error = message
            hideKeyboardFrom(textInputEditText)
            return false
        }else{
            textInputLayout.isErrorEnabled = false
        }
        return true
    }
    fun isInputDate(message: String, textView: TextView, textInputLayout: TextInputLayout): Boolean {
        if(textView.text.trim().isEmpty()){
            textInputLayout.error = message
            return false
        }else{
            textInputLayout.isErrorEnabled = false
        }
        return true
    }
    fun isInputContents(message: String, relativeLayout: RelativeLayout, textInputLayout: TextInputLayout): Boolean{
        if(relativeLayout.childCount == 0){
            textInputLayout.error = message
            return false
        }else{
            textInputLayout.isErrorEnabled = false
        }
        return true
    }

    fun isSameTitle(message: String, textInputEditText: TextInputEditText, textInputLayout: TextInputLayout):Boolean{
        DBManagerMemory.init(mContext)
        val mTitle = textInputEditText.text.toString()
        val cursor = DBManagerMemory.getMemoryAllWithCursor()
        cursor.moveToFirst()
        if(cursor.count !=0) {
            for (title in 0 until cursor.count) {
                if (mTitle == cursor.getString(1)) {
                    textInputLayout.error = message
                    DBManagerMemory.close()
                    return false
                }
                cursor.moveToNext()
            }
        }
        DBManagerMemory.close()
        return true
    }

    fun hideKeyboardFrom(view:View){
        val imm= mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}