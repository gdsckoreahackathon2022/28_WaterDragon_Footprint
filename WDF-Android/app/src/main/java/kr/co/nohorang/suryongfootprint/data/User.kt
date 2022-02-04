package kr.co.nohorang.suryongfootprint.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// JSON 타입 변환에 사용될 객체(DTO)
// request의 편의성을 고려해서 선언할 것.
data class User (@SerializedName("userId")var user_id: String?,
                 @SerializedName("userEmail")var user_email: String?,
                 @SerializedName("userPw")var user_pw: String?,
                 @SerializedName("userName")var user_name: String?,
                 @SerializedName("userNickname")var user_nickname:String?) :Serializable {
}

data class LoginRequestDTO(@SerializedName("userId") var user_id: String?,
                           @SerializedName("userPw") var user_pw: String?) :Serializable {
}
