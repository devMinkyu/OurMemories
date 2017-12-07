package com.kotlin.ourmemories.view.place.adapter.data

/**
 * Created by hee on 2017-11-28.
 */
import android.os.Parcel
import android.os.Parcelable

class SiGunGu : Parcelable {

    var name: String? = null
        private set

    constructor(name: String) {
        this.name = name
    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is SiGunGu) return false

        val siGunGu = o as SiGunGu?

        return if (name != null) name == siGunGu!!.name else siGunGu!!.name == null
    }

    override fun hashCode(): Int {
        return if (name != null) name!!.hashCode() else 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<SiGunGu> = object : Parcelable.Creator<SiGunGu> {
            override fun createFromParcel(`in`: Parcel): SiGunGu {
                return SiGunGu(`in`)
            }

            override fun newArray(size: Int): Array<SiGunGu?> {
                return arrayOfNulls(size)
            }
        }
    }
}
