package com.kotlin.ourmemories.data.source.profile

/**
 * Created by kimmingyu on 2017. 11. 3..
 */
// 자동 로그인의 서버의 데이터를 받는 부분
data class UserProfileResult(val userId:String, val userName:String, val userEmail:String, val userProfileImageUrl:String, val authLogin:String)
data class UserProfile(val IsSuccess:String, val userProfileResult:UserProfileResult)