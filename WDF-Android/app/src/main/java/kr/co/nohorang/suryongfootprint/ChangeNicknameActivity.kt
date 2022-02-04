package kr.co.nohorang.suryongfootprint

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import kr.co.nohorang.suryongfootprint.data.User
import kr.co.nohorang.suryongfootprint.databinding.ActivityChangeNicknameBinding
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeNicknameActivity : AppCompatActivity() {

    // 중복 여부
    var isFine: Boolean = false

    val binding by lazy { ActivityChangeNicknameBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val current_user_id:String=intent.getStringExtra("current_user_id").toString()
        // 툴바 뒤로가기 버튼 - 액티비티 종료
        binding.backBtn.setOnClickListener {
            finish()
        }

        // EditText 입력 중 외부 터치 시 키보드 내리기
        binding.layout.setOnClickListener {
            hideKeyboard(binding.editTextTextPersonName3)
        }
        binding.editTextTextPersonName3.addTextChangedListener { //editText에 글자가 입력되면 중복확인 변수 false으로 초기화
            isFine=false
            binding.changeBtn.isEnabled = false
            binding.changeBtn.setBackgroundColor(Color.parseColor("#CBCBCB"))
            binding.nicknameStateText.setTextColor(Color.parseColor("#ED5555"))
            binding.nicknameStateText.setText("중복 확인을 해주세요")
        }
        // 중복 확인 버튼 클릭
        binding.dupeBtn.setOnClickListener {
            //중복확인 코드
            var input_nick = binding.editTextTextPersonName3.text.toString().trim()
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
                            binding.nicknameStateText.setTextColor(Color.parseColor("#ED5555"))
                            binding.nicknameStateText.text = "중복된 닉네임입니다."
                            isFine = false
                            binding.changeBtn.isEnabled = false
                            binding.changeBtn.setBackgroundColor(Color.parseColor("#CBCBCB"))
                        } else  {
                            Log.d("FIND_ID_T", "status : 사용 가능 닉네임")
                            binding.nicknameStateText.setTextColor(Color.parseColor("#ACC236"))
                            binding.nicknameStateText.text = "사용 가능한 닉네임입니다."

                            isFine = true
                            binding.changeBtn.isEnabled = true
                            binding.changeBtn.setBackgroundColor(Color.parseColor("#537BC4"))
                        }
                    }

                    //request, response 실패
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        t.message?.let { Log.e("FIND_ID_F", it) }
                    }
                })
            }
            else{
                isFine = false
                binding.changeBtn.isEnabled = false
                binding.changeBtn.setBackgroundColor(Color.parseColor("#CBCBCB"))
                binding.nicknameStateText.setTextColor(android.graphics.Color.parseColor("#ED5555"))
                binding.nicknameStateText.text = "내용을 입력해주세요."

            }
        }

        // 확인 버튼 클릭 - 닉네임 변경
        binding.changeBtn.setOnClickListener {
            // user id와 변경할 닉네임 정보 받아오기
            val userIdData:String = current_user_id//intent로 user_id 받아오기
            val nicknameData = binding.editTextTextPersonName3.text.toString().trim()
            var newNickUser = User(userIdData, "", "", "", nicknameData)

            //response로 가져올 data 선언
            var responseUser: User? = null

            //Retrofit 통신 - updateNickname
            RetrofitBuilder.api.updateNickname(userIdData, newNickUser)
                .enqueue(object : Callback<User> {
                    //request, response 정상 수행
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        responseUser = response.body()
                        Log.d("UPDATE_NICKNAME_T", "response : " + responseUser?.toString())
                        Log.d("UPDATE_NICKNAME_T", "user_id : " + responseUser?.user_id)
                        Log.d(
                            "UPDATE_NICKNAME_T",
                            "New user_nickname : " + responseUser?.user_nickname
                        )

                        Toast.makeText(
                            this@ChangeNicknameActivity,
                            "닉네임이 변경되었습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }

                    //request, response 실패
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        t.message?.let { Log.e("UPDATE_NICKNAME_F", it) }
                    }
                })
        }
    }

    // 키보드 비활성화 함수
    fun hideKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}