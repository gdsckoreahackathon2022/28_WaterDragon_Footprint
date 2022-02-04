package kr.co.nohorang.suryongfootprint

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import kr.co.nohorang.suryongfootprint.SettingActivity
import kr.co.nohorang.suryongfootprint.databinding.ActivityMainBinding
import kr.co.nohorang.suryongfootprint.databinding.ActivityMypageEditBinding

class MypageEditActivity : AppCompatActivity() {
    val binding by lazy { ActivityMypageEditBinding.inflate(layoutInflater) }
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
            hideKeyboard(binding.editText)
        }

        //확인버튼
        binding.confirmBtn.setOnClickListener() {
            Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
            //글 서버에 저장하기
        }
    }

    // 키보드 비활성화 함수
    fun hideKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}
