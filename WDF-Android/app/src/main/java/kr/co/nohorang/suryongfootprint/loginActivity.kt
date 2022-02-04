package kr.co.nohorang.suryongfootprint

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import kr.co.nohorang.suryongfootprint.data.LoginRequestDTO
import kr.co.nohorang.suryongfootprint.data.User
import kr.co.nohorang.suryongfootprint.databinding.ActivityLoginBinding
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class loginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // EditText 입력 중 외부 터치 시 키보드 내리기
        binding.layout.setOnClickListener {
            hideKeyboard()
        }

        // 로그인 테스트
        binding.loginBtn.setOnClickListener {
            //request로 전송할(회원가입할) user 정보 받아오기(id, password)
            var id:String = binding.loginIdEdit.text.toString()
            var pwd:String = binding.loginPasswordEdit.text.toString()
            id = id.trim()
            pwd = pwd.trim()
            if (!id.isEmpty() && !pwd.isEmpty()) {
                var loginInfo = LoginRequestDTO(id, pwd)

                //response로 가져올 data 선언
                var responseUser: User? = null

                //Retrofit 통신 - doLogin
                RetrofitBuilder.api.doLogin(loginInfo).enqueue(object : Callback<User> {
                    // 통신 성공
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        responseUser = response.body()
                        if (responseUser != null) { //로그인 성공
                            Log.d("LOGIN_T", "Login user : " + responseUser?.user_id)
                            Log.d("LOGIN_T", "Login user : " + responseUser?.user_pw)
                            // 메인 페이지로 이동
                            val intent = Intent(this@loginActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.putExtra("current_user_id", id)
                            Toast.makeText(this@loginActivity, "로그인되었습니다.", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(intent)
                        } else {
                            Log.d("LOGIN_T", "no user defined")
                            Toast.makeText(this@loginActivity, "잘못된 입력입니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    // 통신 오류
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        t.message?.let { Log.e("LOGIN_F", it) }
                    }
                })
            } else {
                Toast.makeText(this@loginActivity, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
            }

        }
        binding.loginFindIdBtn.setOnClickListener {
            val intent = Intent(this, FindIdActivity::class.java)
            startActivity(intent)
        }
        binding.loginFindPasswordBtn.setOnClickListener {
            val intent = Intent(this, FindPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.loginSignupBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    // 키보드 비활성화 함수
    fun hideKeyboard() {
        val editText1 = binding.loginIdEdit
        val editText2 = binding.loginPasswordEdit
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText1.windowToken, 0)
        imm.hideSoftInputFromWindow(editText2.windowToken, 0)
    }
}