package kr.co.nohorang.suryongfootprint

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kr.co.nohorang.suryongfootprint.databinding.ActivityFindPasswordBinding
import kr.co.nohorang.suryongfootprint.databinding.ActivityPostBinding
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindPasswordActivity : AppCompatActivity() {
    val binding by lazy { ActivityFindPasswordBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val current_user_id=intent.getStringExtra("current_user_id")
        // 툴바 뒤로가기 버튼 - 액티비티 종료
        binding.backBtn.setOnClickListener {
            finish()
        }

        // EditText 입력 중 외부 터치 시 키보드 내리기
        binding.layout.setOnClickListener {
            hideKeyboard()
        }

        //비밀번호 찾기
        binding.findPwdConfirmBtn.setOnClickListener {
            //u_name과 u_email 받아오기
            var u_name = binding.findPwdNameEdit.text.toString().trim()
            var u_id = binding.findPwdIdEdit.text.toString().trim()
            var u_email = binding.findPwdEmailEdit.text.toString().trim()
            if(!u_id.isEmpty()&&!u_name.isEmpty()&&!u_email.isEmpty()) {
                //response로 가져올 data 선언
                var responsePW: String? = null

                //Retrofit 통신 - findUserId
                RetrofitBuilder.api.findUserPW(u_name, u_id, u_email).enqueue(object :
                    Callback<String> {
                    //request, response 정상 수행
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        responsePW = response.body().toString()
                        Log.d("FIND_PW_T", "response : " + response.body().toString())
                        if (responsePW == "null") {
                            Log.d("FIND_PW_T", "status : 사용자 없음.")
                            Toast.makeText(
                                this@FindPasswordActivity,
                                "잘못된 입력입니다.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            // 알림창
                            val alertDialogBuilder: AlertDialog.Builder =
                                AlertDialog.Builder(this@FindPasswordActivity)
                            // 알림창 제목 및 선택지 설정
                            alertDialogBuilder.setTitle("비밀번호 찾기 완료")
                            alertDialogBuilder.setMessage(response.body().toString())
                            // 다이얼로그 생성
                            val alertDialog: AlertDialog = alertDialogBuilder.create()
                            // 다이얼로그 보여주기
                            alertDialog.show()
                        }
                    }

                    //request, response 실패
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        t.message?.let { Log.e("FIND_PW_F", it) }
                        Toast.makeText(
                            this@FindPasswordActivity,
                            "오류가 발생하였습니다.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    }
                })
            }
            else{
                Toast.makeText(this,"내용을 모두 입력해주세요",Toast.LENGTH_LONG).show()
            }
        }
    }

    // 키보드 비활성화 함수
    fun hideKeyboard() {
        val editText1 = binding.findPwdNameEdit
        val editText2 = binding.findPwdIdEdit
        val editText3 = binding.findPwdEmailEdit
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText1.windowToken, 0)
        imm.hideSoftInputFromWindow(editText2.windowToken, 0)
        imm.hideSoftInputFromWindow(editText3.windowToken, 0)
    }
}