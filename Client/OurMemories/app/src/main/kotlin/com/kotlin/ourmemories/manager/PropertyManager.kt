package com.kotlin.ourmemories.manager

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by kimmingyu on 2017. 11. 2..
 */
// 공유 저장소 한번 로그인된 유저라면 자동 로그인 싱글톤으로 구현
object PManager{
    private var pManager:PropertyManager? = null

    fun init(){
        if(pManager == null)
            pManager = PropertyManager()
    }
    // 유저 이름
    fun getUserName():String = pManager?.mProfile!!.getString(PropertyManager.KEY_USERNAME,"") // 만약에 값이 없으면 ""으로 나온다
    fun setUserName(userName: String){
        pManager?.mEditor!!.putString(PropertyManager.KEY_USERNAME, userName)
        pManager?.mEditor!!.commit() // 저장 후 완료한다
    }
    // 유저 아이디
    fun getUserId():String = pManager?.mProfile!!.getString(PropertyManager.KEY_USERID,"")
    fun setUserId(userId: String){
        pManager?.mEditor!!.putString(PropertyManager.KEY_USERID, userId)
        pManager?.mEditor!!.commit()
    }
    // 유저 프로필 사진
    fun getUserProfileImageUrl():String = pManager?.mProfile!!.getString(PropertyManager.KEY_PROFILEIMAGEURL,"")
    fun setUserProfileImageUrl(userProfileImageUrl: String){
        pManager?.mEditor!!.putString(PropertyManager.KEY_PROFILEIMAGEURL, userProfileImageUrl)
        pManager?.mEditor!!.commit()
    }
    // 유저 이메일
    fun getUserEmail():String = pManager?.mProfile!!.getString(PropertyManager.KEY_USEREMAIL,"")
    fun setUserEmail(userEmail: String){
        pManager?.mEditor!!.putString(PropertyManager.KEY_USEREMAIL, userEmail)
        pManager?.mEditor!!.commit()
    }
    // 로그인 여부 0-> 로그아웃 상태 1-> 로그인 상태
    fun getUserIsLogin():String = pManager?.mProfile!!.getString(PropertyManager.KEY_ISLOGIN,"0")
    fun setUserIsLogin(userIsLogin: String){
        pManager?.mEditor!!.putString(PropertyManager.KEY_FACEBOOK_ID, userIsLogin)
        pManager?.mEditor!!.commit()
    }
    // 페이스북 토큰
    fun getUserFacebookId():String = pManager?.mProfile!!.getString(PropertyManager.KEY_FACEBOOK_ID,"")
    fun setUserFacebookId(userFacebookId: String){
        pManager?.mEditor!!.putString(PropertyManager.KEY_FACEBOOK_ID, userFacebookId)
        pManager?.mEditor!!.commit()
    }
    // FCM 토큰
    fun getUserFcmRegId():String = pManager?.mProfile!!.getString(PropertyManager.KEY_FCM_REG_ID,"")
    fun getUserFcmRegId(userFcmRegId: String){
        pManager?.mEditor!!.putString(PropertyManager.KEY_FCM_REG_ID, userFcmRegId)
        pManager?.mEditor!!.commit()
    }
    // 알람 뱃지 숫자
    fun getBadgeNumber():Int = pManager?.mProfile!!.getInt(PropertyManager.ALARM_BADGE_NUMBER,PropertyManager.badgeNumber)
    fun setBadgeNumber(badgeNumber: Int){
        pManager?.mEditor!!.putInt(PropertyManager.ALARM_BADGE_NUMBER, badgeNumber)
        pManager?.mEditor!!.commit()
    }
}



class PropertyManager {
    companion object {
        // 일반 사용자 정보
        val KEY_USERNAME = "userName"
        val KEY_PROFILEIMAGEURL = "userProfileImageUrl"
        val KEY_USEREMAIL = "userEmail"
        val KEY_USERID = "userId"
        val KEY_ISLOGIN = "isLogin"

        // SNS 토큰
        val KEY_FACEBOOK_ID = "facebookId"
        val KEY_FCM_REG_ID = "fcmToken"
        val ALARM_BADGE_NUMBER = "alarmBadgeNumber"

        //FCM알람관련 뱃지카운터.//
        var badgeNumber = 0
    }
    val context:Context = MyApplication.context
    val mProfile: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }
    val mEditor:SharedPreferences.Editor by lazy {
        mProfile.edit()
    }
}