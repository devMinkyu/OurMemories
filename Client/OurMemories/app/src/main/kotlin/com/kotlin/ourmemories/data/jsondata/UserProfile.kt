package com.kotlin.ourmemories.data.source.autologin

/**
 * Created by kimmingyu on 2017. 11. 3..
 */
// 자동 로그인의 서버의 데이터를 받는 부분
data class UserProfileResult(val userId:String, val userName:String, val userEmail:String, val userProfileImageUrl:String, val authLogin:String)
data class UserProfileMemoryResult(val _id:String, val memoryTitle:String,val memoryFromDate:String, val memoryToDate:String?, val memoryLatitude:String,
                                 val memoryLongitude:String, val memoryNation:String, val memoryClassification:String)

data class UserProfile(val isSuccess:String, val userProfileResult:UserProfileResult, val userProfileMemoryResult: ArrayList<UserProfileMemoryResult>?)