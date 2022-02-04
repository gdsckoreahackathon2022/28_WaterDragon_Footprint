package kr.co.nohorang.suryongfootprint

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kr.co.nohorang.suryongfootprint.databinding.ActivityPostBinding
import kr.co.nohorang.suryongfootprint.databinding.ActivitySettingBinding
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity : AppCompatActivity() {
    val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val current_user_id:String=intent.getStringExtra("current_user_id").toString()

        // 툴바 뒤로가기 버튼 - 액티비티 종료
        binding.backBtn.setOnClickListener {
            finish()
        }

        // 설정 메뉴 버튼 - 액티비티 이동
        binding.changeNicknameText.setOnClickListener {
            val intent = Intent(this, ChangeNicknameActivity::class.java)
            intent.putExtra("current_user_id", current_user_id)
            startActivity(intent)
        }
        binding.changePwdText.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            intent.putExtra("current_user_id", current_user_id)
            startActivity(intent)
        }
        binding.questionText.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("current_user_id", current_user_id)
            startActivity(intent)
        }
        binding.logoutText.setOnClickListener {
            // 알림창
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            // 알림창 제목 및 선택지 설정
            alertDialogBuilder.setMessage("로그아웃하시겠습니까?")
            alertDialogBuilder.setPositiveButton("예"){ dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(this, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, loginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            alertDialogBuilder.setNegativeButton("아니오") {dialogInterface: DialogInterface, i:Int ->
                Log.d("SETTING", "로그아웃 취소")
            }
            // 다이얼로그 생성
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            // 다이얼로그 보여주기
            alertDialog.show()
        }
        binding.deleteText.setOnClickListener {
            // 알림창
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            // 알림창 제목 및 선택지 설정
            alertDialogBuilder.setMessage("정말 탈퇴하시겠습니까?")
            alertDialogBuilder.setPositiveButton("예"){ dialogInterface: DialogInterface, i: Int ->

                // user_id 받아오기

                // Retrofit 통신 - deleteUser
                RetrofitBuilder.api.deleteUser(current_user_id).enqueue(object : Callback<Void> {
                    //request, response 정상 수행
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("DELETE_USER_T", "정상 수행")
                        Toast.makeText(this@SettingActivity, "탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SettingActivity, loginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("DELETE_USER_F", "서버 응답 없음")
                    }
                })
            }
            alertDialogBuilder.setNegativeButton("아니오") {dialogInterface: DialogInterface, i:Int ->
                Log.d("SETTING", "탈퇴 취소")
            }
            // 다이얼로그 생성
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            // 다이얼로그 보여주기
            alertDialog.show()
        }
    }
}