package com.kotlin.phonebook.adapter

import android.content.Context
import android.database.Cursor
import android.database.DataSetObserver
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by kimmingyu on 2017. 10. 12..
 */
// ListView에 기본적으로 제공해주는 CursorAdapter를 RecyclerView로 바꿨을 때 사용하기 위한 부모 클래스
open class CursorRecyclerViewAdapter<VH : RecyclerView.ViewHolder>(context: Context, cursor: Cursor) : RecyclerView.Adapter<VH>() {
    private val context: Context


    var cursor: Cursor
    private var dataValid : Boolean
    private var rowIdColumn : Int
    private val dataSetObserver : DataSetObserver
    init{
        this.context = context
        this.cursor = cursor
        dataValid = cursor !=null
        rowIdColumn = when(dataValid){
            true -> {this.cursor.getColumnIndex("_id")}
            false-> { -1 }
        }
        dataSetObserver = NotifyingDataSetObserver()
        if(this.cursor != null)
            this.cursor.registerDataSetObserver(dataSetObserver)
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        if(dataValid and (this.cursor != null))
            return this.cursor.count

        return 0

    }

    override fun getItemId(position: Int): Long {
        if(dataValid and (this.cursor != null) and (this.cursor.moveToPosition(position)))
            return this.cursor.getLong(rowIdColumn)
        return 0
    }
    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    open fun onBindViewHolder(holder: VH, cursor: Cursor){}

    override fun onBindViewHolder(holder: VH, position: Int) {
        if(!dataValid)
            throw IllegalAccessException("this should only be called when the cursor is valid")
        if(!this.cursor.moveToPosition(position))
            throw IllegalAccessException("couldn't move cursor to position ${position}")
        onBindViewHolder(holder, this.cursor)
    }

    fun changeCursor(cursor: Cursor){
        var old: Cursor? = swapCursor(cursor)
        if(old != null)
            old.close()
    }
    fun swapCursor(newCursor: Cursor) : Cursor?{
        if(newCursor == this.cursor)
            return null
        val oldCurcor : Cursor = this.cursor
        if((oldCurcor != null) and (dataSetObserver != null))
            oldCurcor.unregisterDataSetObserver(dataSetObserver)
        this.cursor = newCursor
        if(this.cursor != null) {
            if(dataSetObserver != null)
                this.cursor.registerDataSetObserver(dataSetObserver)
            rowIdColumn = newCursor.getColumnIndexOrThrow("_id")
            dataValid = true
            notifyDataSetChanged()
        } else{
            rowIdColumn = -1
            dataValid = false
            notifyDataSetChanged()
        }
        return oldCurcor
    }



    private inner class NotifyingDataSetObserver : DataSetObserver(){
        override fun onChanged() {
            super.onChanged()
            dataValid = true
            notifyDataSetChanged()
        }

        override fun onInvalidated() {
            super.onInvalidated()
            dataValid = false
            notifyDataSetChanged()
        }

    }
}