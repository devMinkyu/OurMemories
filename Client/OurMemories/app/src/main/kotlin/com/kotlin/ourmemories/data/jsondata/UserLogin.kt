package com.kotlin.ourmemories.data.jsondata

/**
 * Created by kimmingyu on 2017. 11. 3..
 */
// 자동 로그인의 서버의 데이터를 받는 부분
data class UserLoginResult(val userId:String, val userName:String, val userEmail:String, val userProfileImageUrl:String)

data class UserLoginMemoryResult(val _id:String, val memoryTitle:String,val memoryFromDate:String, val memoryToDate:String?, val memoryLatitude:String,
                                 val memoryLongitude:String, val memoryNation:String, val memoryClassification:String)

data class UserLogin(val isSuccess:String, val userLoginResult: UserLoginResult, val userLoginMemoryResult: UserLoginMemoryResult)