package kr.co.nohorang.suryongfootprint.api

import kr.co.nohorang.suryongfootprint.data.*
import retrofit2.Call
import retrofit2.http.*

interface ChallengeService {

    //전체 챌린지 가져오기
    @GET("/s-footprint/challenge")
    fun getChallenges():Call<List<Challenge>>

    // 챌린지 참여 createPost
    @POST("/s-footprint/challenge/post")
    fun createPost(@Body pcd : PostCreationDTO): Call<Post>

    //전체 Count 가져오기
    @GET("/s-footprint/challenge/count")
    fun getPostCounts():Call<List<Count>>

    //사용자별 참여(Post) 조회
    @GET("/s-footprint/challenge/post/{user_id}")
    fun getUserPosts(@Path("user_id") user_id:String):Call<List<Post>>

    //챌린지별 참여(Post) 조회
    @GET("/s-footprint/challenge/post/chall/{challenge_id}")
    fun getChallengePosts(@Path("challenge_id") challenge_id:Int):Call<List<Post>>

    //사용자별 챌린지별 참여(Post) 조회
    @GET("/s-footprint/challenge/post/{user_id}/chall/{challenge_id}")
    fun getUserChallengePosts(@Path("user_id") user_id:String,
                              @Path("challenge_id") challenge_id:Int):Call<List<Post>>

    //사용자별 챌린지별 상태별 참여(Post) 조회
    @GET("/s-footprint/challenge/post/{user_id}/chall/{challenge_id}/{state}")
    fun getUserChallengeStatePosts(@Path("user_id") user_id:String,
                                   @Path("challenge_id") challenge_id:Int,
                                   @Path("state")state:Int):Call<List<Post>>

    //참여(Post) 수정
    @PATCH("/s-footprint/challenge/post/{post_id}/update")
    fun updatePost(@Path("post_id") post_id:Int,
                   @Body p_dto:PostUpdateDTO ):Call<Post>

    //참여(Post) 삭제
    @DELETE("/s-footprint/challenge/post/{post_id}")
    fun deletePost():Call<Void>
}