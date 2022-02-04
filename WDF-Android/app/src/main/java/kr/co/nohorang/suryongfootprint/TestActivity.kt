package kr.co.nohorang.suryongfootprint;
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.co.nohorang.suryongfootprint.data.LoginRequestDTO
import kr.co.nohorang.suryongfootprint.data.User
import kr.co.nohorang.suryongfootprint.databinding.ActivityTestBinding
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//api test
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 회원가입 테스트
        binding.SignupTestBtn.setOnClickListener {
            //request로 전송할(회원가입할) user 정보 받아오기
            var newUser = User("abcd", "abcd123@gmail.com", "1234123@", "coco", "coconut")

            //response로 가져올 data 선언
            var responseUser: User?=null

            //Retrofit 통신 - createUser
            RetrofitBuilder.api.createUser(newUser).enqueue(object : Callback<User> {
                //request, response 정상 수행
                override fun onResponse(call : Call<User>, response: Response<User>){
                    responseUser=response.body()
                    Log.d("SIGNUP_T","response : "+responseUser?.toString())
                    Log.d("SIGNUP_T","New user_id : "+responseUser?.user_id)
                    Log.d("SIGNUP_T","New user_pw : "+responseUser?.user_pw)
                }
                //request, response 실패
                override fun onFailure(call : Call<User>, t: Throwable) {
                    t.message?.let { Log.e("SIGNUP_F", it) }
                }
            })

        }

        // 로그인 테스트
        binding.LoginTestBtn.setOnClickListener{
            //request로 전송할(회원가입할) user 정보 받아오기
            var loginInfo = LoginRequestDTO("flora", "1111")

            //response로 가져올 data 선언
            var responseUser: User?=null

            //Retrofit 통신 - doLogin
            RetrofitBuilder.api.doLogin(loginInfo).enqueue(object : Callback<User> {
                // 통신 성공
                override fun onResponse(call : Call<User>, response: Response<User>){
                    responseUser=response.body()
                    if (responseUser!=null) { //로그인 성공
                        Log.d("LOGIN_T", "Login user : " + responseUser?.user_id)
                        Log.d("LOGIN_T", "Login user : " + responseUser?.user_pw)
                    }
                    else
                        Log.d("LOGIN_T","no user defined")
                }
                // 통신 오류
                override fun onFailure(call : Call<User>, t: Throwable) {
                    t.message?.let { Log.e("LOGIN_F", it) }
                }
            })
        }

        //닉네임 변경
        binding.updateNicknameTestBtn.setOnClickListener {
            //user id와 변경할 닉네임 정보 받아오기
            var newNickUser = User("test_id123", "", "", "", "coconut")
            var user_id = "test_id123"
            //response로 가져올 data 선언
            var responseUser: User?=null

            //Retrofit 통신 - updateNickname
            RetrofitBuilder.api.updateNickname(user_id,newNickUser).enqueue(object : Callback<User> {
                //request, response 정상 수행
                override fun onResponse(call : Call<User>, response: Response<User>){
                    responseUser=response.body()
                    Log.d("UPDATE_NICKNAME_T","response : "+responseUser?.toString())
                    Log.d("UPDATE_NICKNAME_T","user_id : "+responseUser?.user_id)
                    Log.d("UPDATE_NICKNAME_T","New user_nickname : "+responseUser?.user_nickname)
                }
                //request, response 실패
                override fun onFailure(call : Call<User>, t: Throwable) {
                    t.message?.let { Log.e("UPDATE_NICKNAME_F", it) }
                }
            })

        }

        //비밀번호 변경
        binding.updatePasswordTestBtn.setOnClickListener {
            //user id와 변경할 pw 정보 받아오기
            var newNickUser = User("test_id123", "", "1234", "", "")
            var user_id = "test_id123"
            //response로 가져올 data 선언
            var responseUser: User?=null

            //Retrofit 통신 - updatePw
            RetrofitBuilder.api.updatePW(user_id,newNickUser).enqueue(object : Callback<User> {
                //request, response 정상 수행
                override fun onResponse(call : Call<User>, response: Response<User>){
                    responseUser=response.body()
                    Log.d("UPDATE_PASSWORD_T","response : "+responseUser?.toString())
                    Log.d("UPDATE_PASSWORD_T","user_id : "+responseUser?.user_id)
                    Log.d("UPDATE_PASSWORD_T","New user_pw : "+responseUser?.user_pw)
                }
                //request, response 실패
                override fun onFailure(call : Call<User>, t: Throwable) {
                    t.message?.let { Log.e("UPDATE_PASSWORD_F", it) }
                }
            })

        }

        //아이디 찾기
        binding.findUserIdTestBtn.setOnClickListener {
            //user_name과 user_email 받아오기
            var user_name = "coco"
            var user_email = "abcd123@gmail.com"

            //response로 가져올 data 선언
            var responseId: String?=null

            //Retrofit 통신 - findUserId
            RetrofitBuilder.api.findUserId(user_name,user_email).enqueue(object : Callback<String> {
                //request, response 정상 수행
                override fun onResponse(call : Call<String>, response: Response<String>){
                    responseId=response.body().toString()
                    Log.d("FIND_ID_T","response : "+response.body().toString())
                    if(responseId==null){Log.d("FIND_ID_T","status : 사용자 없음.")}
                }
                //request, response 실패
                override fun onFailure(call : Call<String>, t: Throwable) {
                    t.message?.let { Log.e("FIND_ID_F", it) }
                }
            })

        }

        //비밀번호 찾기
        binding.findUserPWTestBtn.setOnClickListener {
            //user_name과 user_email 받아오기
            var user_id = "test_id123"
            var user_name = "coco"
            var user_email = "abcd123@gmail.com"

            //response로 가져올 data 선언
            var responsePW: String?=null

            //Retrofit 통신 - findUserId
            RetrofitBuilder.api.findUserPW(user_name,user_id,user_email).enqueue(object : Callback<String> {
                //request, response 정상 수행
                override fun onResponse(call : Call<String>, response: Response<String>){
                    responsePW=response.body().toString()
                    Log.d("FIND_PW_T","response : "+response.body().toString())
                    if(responsePW==null){Log.d("FIND_PW_T","status : 사용자 없음.")}
                }
                //request, response 실패
                override fun onFailure(call : Call<String>, t: Throwable) {
                    t.message?.let { Log.e("FIND_PW_F", it) }
                }
            })

        }

        //회원 탈퇴
        binding.deleteUserTestBtn.setOnClickListener {
            //user_id 받아오기
            var user_id = "test_id456"

            //Retrofit 통신 - deleteUser
            RetrofitBuilder.api.deleteUser(user_id).enqueue(object : Callback<Void> {
                //request, response 정상 수행
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.d("DELETE_USER_T", "정상 수행")
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("DELETE_USER_F", "서버 응답 없음")
                }
            })

        }
    }
}