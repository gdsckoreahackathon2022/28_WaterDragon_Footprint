package kr.co.nohorang.suryongfootprint

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import kr.co.nohorang.suryongfootprint.data.User
import kr.co.nohorang.suryongfootprint.databinding.ActivitySignUpBinding
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    var isNicknameFine: Boolean = false
    var isIdFine: Boolean = false
    var isEmailFine: Boolean = false
    var isPasswordFine: Boolean = false

    val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.signupNicknameText.addTextChangedListener { //editText에 글자가 입력되면 중복확인 변수 0으로 초기화
            isNicknameFine=false
            binding.nicknameCheckText.setTextColor(Color.parseColor("#ED5555"))
            binding.nicknameCheckText.setText("중복 확인을 해주세요")
        }
        binding.signupIdText.addTextChangedListener {
            isIdFine=false
            binding.idCheckText.setTextColor(Color.parseColor("#ED5555"))
            binding.idCheckText.setText("중복 확인을 해주세요")
        }
        binding.signupEmailText.addTextChangedListener {
            isEmailFine=false
            binding.emailCheckText.setTextColor(Color.parseColor("#ED5555"))
            binding.emailCheckText.setText("중복 확인을 해주세요")
        }


        binding.nicknameCheckBtn.setOnClickListener {
            var input_nick = binding.signupNicknameText.text.toString().trim()
            if(!input_nick.isEmpty()){
                //response로 가져올 data 선언
                var response_nick: String? = null

                //Retrofit 통신
                RetrofitBuilder.api.existUserNickname(input_nick).enqueue(object : Callback<String> {
                    //request, response 정상 수행
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        response_nick = response.body().toString()
                        Log.d("FIND_ID_T", "response : " + response.body().toString())
                        if (response_nick == input_nick) {
                            Log.d("FIND_ID_T", "status : 사용 불가능 닉네임")
                            binding.nicknameCheckText.setTextColor(Color.parseColor("#ED5555"))
                            binding.nicknameCheckText.setText("사용 불가능한 닉네임입니다.")
                            isNicknameFine=false
                        } else  {
                            Log.d("FIND_ID_T", "status : 사용 가능 닉네임")
                            binding.nicknameCheckText.setTextColor(Color.parseColor("#ACC236"))
                            binding.nicknameCheckText.setText("사용 가능한 닉네임입니다.")
                            isNicknameFine=true
                        }
                    }

                    //request, response 실패
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        t.message?.let { Log.e("FIND_ID_F", it) }
                    }
                })
            }
            else{
                binding.nicknameCheckText.setTextColor(Color.parseColor("#ED5555"))
                binding.nicknameCheckText.setText("내용을 입력해주세요.")
                isNicknameFine=false
            }

        }
        binding.idCheckBtn.setOnClickListener { //중복 확인 버튼 누를 시 쿼리문 실행
            //input id 받아오기
            var input_id = binding.signupIdText.text.toString().trim()
            if(!input_id.isEmpty()){
                //response로 가져올 data 선언
                var response_id: String? = null

                //Retrofit 통신
                RetrofitBuilder.api.existUserId(input_id).enqueue(object : Callback<String> {
                    //request, response 정상 수행
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        response_id = response.body().toString()
                        Log.d("FIND_ID_T", "response : " + response.body().toString())
                        if (response_id == input_id) {//input 아이디와 같은 아이디가 존재
                            Log.d("FIND_ID_T", "status : 사용 불가능 아이디")
                            binding.idCheckText.setTextColor(Color.parseColor("#ED5555"))
                            binding.idCheckText.setText("사용 불가능한 아이디입니다.")
                            isIdFine=false

                        } else{
                            Log.d("FIND_ID_T", "status : 사용 가능 아이디")
                            binding.idCheckText.setTextColor(Color.parseColor("#ACC236"))
                            binding.idCheckText.setText("사용 가능한 아이디입니다.")
                            isIdFine=true
                        }
                    }

                    //request, response 실패
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        t.message?.let { Log.e("FIND_ID_F", it) }
                    }
                })
            }
            else{
                binding.idCheckText.setTextColor(Color.parseColor("#ED5555"))
                binding.idCheckText.setText("내용을 입력해주세요.")
                isIdFine=false
            }

        }
        binding.emailCheckBtn.setOnClickListener { //중복 확인 버튼 누를 시 쿼리문 실행
            //input id 받아오기
            var input_email = binding.signupEmailText.text.toString().trim()
            if(!input_email.isEmpty()){
                //response로 가져올 data 선언
                var response_email: String? = null

                //Retrofit 통신
                RetrofitBuilder.api.existUserEmail(input_email).enqueue(object : Callback<String> {
                    //request, response 정상 수행
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        response_email = response.body().toString()
                        Log.d("FIND_ID_T", "response : " + response.body().toString())
                        if (response_email == input_email) {
                            Log.d("FIND_ID_T", "status : 사용 불가능 이메일")
                            binding.emailCheckText.setTextColor(Color.parseColor("#ED5555"))
                            binding.emailCheckText.setText("사용 불가능한 이메일입니다.")
                            isEmailFine=false

                        } else {
                            Log.d("FIND_ID_T", "status : 사용 가능 이메일")
                            binding.emailCheckText.setTextColor(Color.parseColor("#ACC236"))
                            binding.emailCheckText.setText("사용 가능한 이메일입니다.")
                            isEmailFine=true
                        }
                    }

                    //request, response 실패
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        t.message?.let { Log.e("FIND_ID_F", it) }
                    }
                })
            }
            else{
                binding.emailCheckText.setTextColor(Color.parseColor("#ED5555"))
                binding.emailCheckText.setText("내용을 입력해주세요.")
                isEmailFine=false
            }


        }

        // 툴바 뒤로가기 버튼 - 액티비티 종료
        binding.backBtn.setOnClickListener {
            finish()
        }

        // EditText 입력 중 외부 터치 시 키보드 내리기
        binding.layout.setOnClickListener {
            hideKeyboard()
        }
        binding.scroll.setOnClickListener {
            hideKeyboard()
        }
        binding.linear.setOnClickListener {
            hideKeyboard()
        }

        // 회원가입 테스트
        binding.signupBtn.setOnClickListener {
            //request로 전송할(회원가입할) user 정보 받아오기(id, email, pw, name, nickname)
            var id = binding.signupIdText.text.toString().trim()
            var email = binding.signupEmailText.text.toString().trim()
            var pw = binding.signupPasswordText.text.toString().trim()
            var repw=binding.signupRePasswordText.text.toString().trim()
            var name = binding.signupNameText.text.toString().trim()
            var nickname = binding.signupNicknameText.text.toString().trim()
            if (!id.isEmpty() && !email.isEmpty() && !pw.isEmpty() && !name.isEmpty() && !nickname.isEmpty())//회원가입 시도
            {
                if(pw==repw)
                    isPasswordFine=true
                if (isNicknameFine && isIdFine && isEmailFine && isPasswordFine) {
                    var newUser = User(id, email, pw, name, nickname)

                    //response로 가져올 data 선언
                    var responseUser: User? = null

                    //Retrofit 통신 - createUser
                    RetrofitBuilder.api.createUser(newUser).enqueue(object : Callback<User> {
                        //request, response 정상 수행
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            responseUser = response.body()
                            Log.d("SIGNUP_T", "response : " + responseUser?.toString())
                            Log.d("SIGNUP_T", "New user_id : " + responseUser?.user_id)
                            Log.d("SIGNUP_T", "New user_pw : " + responseUser?.user_pw)

                            Toast.makeText(this@SignUpActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                        //request, response 실패
                        override fun onFailure(call: Call<User>, t: Throwable) {
                            t.message?.let { Log.e("SIGNUP_F", it) }
                            Toast.makeText(this@SignUpActivity, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    isPasswordFine=false
                    Toast.makeText(this, "사용할 수 없는 항목이 있습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "모든 항목을 작성해주세요.", Toast.LENGTH_SHORT).show()
            }

        }

    }

    // 키보드 비활성화 함수
    fun hideKeyboard() {
        val editText1 = binding.signupNicknameText
        val editText2 = binding.signupIdText
        val editText3 = binding.signupEmailText
        val editText4 = binding.signupNameText
        val editText5 = binding.signupPasswordText
        val editText6 = binding.signupRePasswordText
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText1.windowToken, 0)
        imm.hideSoftInputFromWindow(editText2.windowToken, 0)
        imm.hideSoftInputFromWindow(editText3.windowToken, 0)
        imm.hideSoftInputFromWindow(editText4.windowToken, 0)
        imm.hideSoftInputFromWindow(editText5.windowToken, 0)
        imm.hideSoftInputFromWindow(editText6.windowToken, 0)
    }
}