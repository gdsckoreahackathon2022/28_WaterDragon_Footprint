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
import android.widget.ToggleButton
import androidx.core.widget.addTextChangedListener
import kr.co.nohorang.suryongfootprint.data.User
import kr.co.nohorang.suryongfootprint.databinding.ActivityChangePasswordBinding
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    // 비밀번호 확인 여부
    var isOldFine: Boolean = false
    var isNewFine: Boolean = false
    var isPasswordFine: Boolean = false

    val binding by lazy { ActivityChangePasswordBinding.inflate(layoutInflater) }
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
            hideKeyboard()
        }
        binding.oldPwdEdit.addTextChangedListener { //editText에 글자가 입력되면 변수 false으로 초기화
            isOldFine=false
            binding.pwdChangeBtn.isEnabled = false
            binding.pwdChangeBtn.setBackgroundColor(Color.parseColor("#CBCBCB"))
            binding.oldStateText.setTextColor(Color.parseColor("#ED5555"))
            binding.oldStateText.setText("확인 버튼을 눌러 주세요.")
        }
        binding.newPwdEdit1.addTextChangedListener {
            isNewFine=false
            binding.pwdChangeBtn.isEnabled = false
            binding.pwdChangeBtn.setBackgroundColor(Color.parseColor("#CBCBCB"))
            binding.newStateText.setTextColor(Color.parseColor("#ED5555"))
            binding.newStateText.setText("확인 버튼을 눌러 주세요.")
        }
        binding.newPwdEdit2.addTextChangedListener {
            isNewFine=false
            binding.pwdChangeBtn.isEnabled = false
            binding.pwdChangeBtn.setBackgroundColor(Color.parseColor("#CBCBCB"))
            binding.newStateText.setTextColor(Color.parseColor("#ED5555"))
            binding.newStateText.setText("확인 버튼을 눌러 주세요.")
        }

        // 기존 비밀번호 확인 버튼 클릭
        binding.confirmBtn1.setOnClickListener {
            if(!binding.oldPwdEdit.text.toString().trim().isEmpty())
            {
                //기존 비밀번호 가져와서 입력받은 값과 같은지 확인.
                if(true){
                    // 올바른 입력인 경우
                    binding.oldStateText.text = "확인되었습니다."
                    binding.oldStateText.setTextColor(Color.parseColor("#ACC236"))
                    isOldFine = true
                    if (isOldFine && isNewFine) {
                        binding.pwdChangeBtn.isEnabled = true
                        binding.pwdChangeBtn.setBackgroundColor(Color.parseColor("#537BC4"))
                    }
                }
                else{
                    // 잘못된 입력인 경우
                    binding.oldStateText.text = "잘못된 비밀번호입니다."
                    binding.oldStateText.setTextColor(Color.parseColor("#ED5555"))
                    isOldFine = false
                }
            }
            else{
                binding.oldStateText.text = "내용을 입력하세요."
            binding.oldStateText.setTextColor(Color.parseColor("#ED5555"))
            isOldFine = false
            }

        }

        // 새 비밀번호 확인 버튼 클릭
        binding.confirmBtn2.setOnClickListener {
            val newpw=binding.newPwdEdit1.text.toString().trim()
            val newrepw=binding.newPwdEdit2.text.toString().trim()
            if(!newpw.isEmpty()&&!newrepw.isEmpty()){
                if (newpw != newrepw) {
                    // 잘못된 입력인 경우
                    binding.newStateText.text = "잘못된 입력입니다."
                    binding.newStateText.setTextColor(Color.parseColor("#ED5555"))
                    isNewFine = false
                } else {
                    // 올바른 입력인 경우
                    binding.newStateText.text = "비밀번호가 일치합니다."
                    binding.newStateText.setTextColor(Color.parseColor("#ACC236"))
                    isNewFine = true
                }
                if (isOldFine && isNewFine) {
                    binding.pwdChangeBtn.isEnabled = true
                    binding.pwdChangeBtn.setBackgroundColor(Color.parseColor("#537BC4"))
                }
            }
            else{
                binding.newStateText.text = "내용을 입력하세요."
                binding.newStateText.setTextColor(Color.parseColor("#ED5555"))
                isNewFine = false
            }
        }

        // 확인 버튼 클릭 - 닉네임 변경 (+ 중복 확인 여부)
        binding.pwdChangeBtn.setOnClickListener {
            //user id와 변경할 pw 정보 받아오기
            val userIdData:String = current_user_id  // 현재 접속중인 회원 아이디 필요.
            val userPwdData = binding.newPwdEdit1.text.toString().trim()
            var newNickUser = User(userIdData, "", userPwdData, "", "")

            //response로 가져올 data 선언
            var responseUser: User? = null

            //Retrofit 통신 - updatePw
            RetrofitBuilder.api.updatePW(userIdData, newNickUser).enqueue(object : Callback<User> {
                //request, response 정상 수행
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    responseUser = response.body()
                    Log.d("UPDATE_PASSWORD_T", "response : " + responseUser?.toString())
                    Log.d("UPDATE_PASSWORD_T", "user_id : " + responseUser?.user_id)
                    Log.d("UPDATE_PASSWORD_T", "New user_pw : " + responseUser?.user_pw)
                    Toast.makeText(this@ChangePasswordActivity,"비밀번호가 변경 되었습니다.",Toast.LENGTH_LONG).show()
                    finish()
                }

                //request, response 실패
                override fun onFailure(call: Call<User>, t: Throwable) {
                    t.message?.let { Log.e("UPDATE_PASSWORD_F", it) }
                }
            })
        }
    }

    // 키보드 비활성화 함수
    fun hideKeyboard() {
        val editText1 = binding.oldPwdEdit
        val editText2 = binding.newPwdEdit1
        val editText3 = binding.newPwdEdit2
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText1.windowToken, 0)
        imm.hideSoftInputFromWindow(editText2.windowToken, 0)
        imm.hideSoftInputFromWindow(editText3.windowToken, 0)
    }
}