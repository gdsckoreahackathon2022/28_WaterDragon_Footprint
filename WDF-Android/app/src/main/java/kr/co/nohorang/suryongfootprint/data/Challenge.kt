package kr.co.nohorang.suryongfootprint.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

// JSON 타입 변환에 사용될 객체(DTO)
// request의 편의성을 고려해서 선언할 것.
data class Challenge (
                  @SerializedName("challengeId")var challenge_id: Int?,
                  @SerializedName("title")var title: String?,
                  @SerializedName("info")var info:String?,
                  @SerializedName("condition")var condition:Int?,
                  @SerializedName("participants")var participants:Int?
) :Serializable {
}
