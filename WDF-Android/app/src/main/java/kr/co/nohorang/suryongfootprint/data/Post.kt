package kr.co.nohorang.suryongfootprint.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Blob

// JSON 타입 변환에 사용될 객체(DTO)
// request의 편의성을 고려해서 선언할 것.
data class Post (@SerializedName("postId")var post_id: String?,
                 @SerializedName("userId")var user_id: String?,
                 @SerializedName("challengeId")var challenge_id: Int?,
                 @SerializedName("countId")var challenge_count: Int?,
                 @SerializedName("img")var img: Blob?,
                 @SerializedName("content")var content:String?,
                 @SerializedName("state")var state:Int?
) :Serializable {}

data class PostCreationDTO(
    @SerializedName("userId")var user_id: String?,
    @SerializedName("challengeId")var challenge_id: Int?,
    @SerializedName("img")var img: Blob?,
    @SerializedName("content")var content:String?,
    @SerializedName("state")var state:Int?
):Serializable{}

data class PostUpdateDTO(
    @SerializedName("content")var content:String?,
    @SerializedName("state")var state:Int?
):Serializable{}