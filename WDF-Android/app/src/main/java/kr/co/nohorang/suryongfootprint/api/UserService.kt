package kr.co.nohorang.suryongfootprint.api

import kr.co.nohorang.suryongfootprint.data.LoginRequestDTO
import kr.co.nohorang.suryongfootprint.data.User
import retrofit2.Call
import retrofit2.http.*

//사용할 HTTP CRUD 동작들을 정의한 인터페이스
interface UserService {
    //회원가입 - 정상 수행됨
    //request body - 회원가입할 User 정보
    @POST("/s-footprint/user")
    fun createUser(@Body user : User): retrofit2.Call<User>

    //로그인
    //request body - 아이디, 비밀번호
    @POST("/s-footprint/user/login")
    fun doLogin(@Body loginInfo : LoginRequestDTO): retrofit2.Call<User>

    //아이디 찾기
    @GET("/s-footprint/user/user_id")
    fun findUserId(@Query("user_name") user_name : String,
                   @Query("user_email") user_email : String
    ):Call<String>

    //비밀번호 찾기
    @GET("/s-footprint/user/user_pw")
    fun findUserPW(@Query("user_name") user_name : String,
                   @Query("user_id") user_id : String,
                   @Query("user_email") user_email : String
    ):Call<String>

    //닉네임 변경하기
    //Body - User에서 user_nickname값만 사용 -> 나머지는 null 값이어도 됨.
    @PATCH("/s-footprint/user/{user_id}/nickname")
    fun updateNickname(@Path("user_id") user_id:String,
                        @Body user:User) : Call<User>

    //비밀번호 변경하기
    //Body - User에서 user_pw값만 사용 -> 나머지는 null 값이어도 됨.
    @PATCH("/s-footprint/user/{user_id}/pw")
    fun updatePW(@Path("user_id") user_id:String,
                @Body user:User) : Call<User>

    //회원 탈퇴
    @DELETE("/s-footprint/user/{user_id}")
    fun deleteUser(@Path("user_id") user_id:String):Call<Void>

    //아이디 중복 조회
    @GET("/s-footprint/user/available/id")
    fun existUserId(@Query("user_id") user_id:String):Call<String>

    //이메일 중복 조회
    @GET("/s-footprint/user/available/email")
    fun existUserEmail(@Query("user_email") user_email:String):Call<String>

    //닉네임 중복 조회
    @GET("/s-footprint/user/available/nickname")
    fun existUserNickname(@Query("user_nickname") user_nickname:String):Call<String>
}