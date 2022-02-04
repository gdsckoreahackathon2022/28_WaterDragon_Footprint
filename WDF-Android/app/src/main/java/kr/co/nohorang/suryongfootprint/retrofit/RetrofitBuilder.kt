package kr.co.nohorang.suryongfootprint.retrofit

import com.google.gson.GsonBuilder
import kr.co.nohorang.suryongfootprint.api.ChallengeService
import kr.co.nohorang.suryongfootprint.api.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    //사용할 api 인터페이스 선언
    var api: UserService
    var challenge_api: ChallengeService

    val gson = GsonBuilder()
        .setLenient()
        .create()

    init {
        //api 서버 연결
        val retrofit = Retrofit.Builder()
            .baseUrl("http://220.69.171.47:8080")//localhost 로 임시 설정
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        api = retrofit.create(UserService::class.java)
        challenge_api = retrofit.create(ChallengeService::class.java)
    }
}