package kr.co.nohorang.suryongfootprint

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.nohorang.suryongfootprint.data.TestPost
import kr.co.nohorang.suryongfootprint.databinding.ActivityApprovalDetailBinding

class ApprovalDetailActivity : AppCompatActivity() {
    val binding by lazy { ActivityApprovalDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val current_user_id=intent.getStringExtra("current_user_id")
        // 테스트용 참여 데이터 생성
        val data:MutableList<TestPost> = loadTestPostData()
        // 어댑터 생성 후 데이터 저장
        var adapter = PostAdapter()
        adapter.listData = data
        // 어댑터 연결
        binding.recycler.adapter = adapter
        // 레이아웃 매니저 연결
        binding.recycler.layoutManager = LinearLayoutManager(this)

        // 툴바 뒤로가기 버튼 - 액티비티 종료
        binding.backBtn.setOnClickListener {
            finish()
        }

        // 승인하기 버튼 - 버튼 비활성화 및 토스트 발생
        binding.approvalBtn.setOnClickListener{
            binding.approvalBtn.setBackgroundColor(Color.GRAY)
            binding.approvalBtn.isEnabled = false
            Toast.makeText(this, "승인하였습니다.", Toast.LENGTH_SHORT).show()
        }

        // 신고 버튼 - 버튼 비활성화 및 토스트 발생
        binding.reportBtn.setOnClickListener {
            binding.reportBtn.setBackgroundColor(Color.GRAY)
            binding.reportBtn.isEnabled = false
            Toast.makeText(this, "신고하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 테스트용 참여 데이터 생성 함수
    fun loadTestPostData(): MutableList<TestPost> {
        val data: MutableList<TestPost> = mutableListOf()

        for (no in 1..7) {
            val post_id = no
            val user_id = "아이디"
            var challenge_id = 6
            var count_id = 1
            // val img
            val content = "지금까지 챌린지 ${no}번 참여했어요!"
            var state = 0
            var date = System.currentTimeMillis()

            var testPost = TestPost(post_id, user_id, challenge_id, count_id, /* img, */content, state, date)
            data.add(testPost)
        }
        return data
    }
}