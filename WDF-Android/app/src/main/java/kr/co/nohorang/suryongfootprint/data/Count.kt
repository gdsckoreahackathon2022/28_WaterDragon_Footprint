package kr.co.nohorang.suryongfootprint.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// JSON 타입 변환에 사용될 객체(DTO)
// request의 편의성을 고려해서 선언할 것.
data class Count (@SerializedName("CountId")var count_id: String?,
                 @SerializedName("userId")var user_id: String?,
                 @SerializedName("challengeId")var challenge_id: Int?,
                 @SerializedName("challengeCount")var challenge_count: Int?,
                 @SerializedName("postCount")var post_count:Int?,
                  @SerializedName("approvalCount")var approval_count:Int?
) :Serializable {
}
