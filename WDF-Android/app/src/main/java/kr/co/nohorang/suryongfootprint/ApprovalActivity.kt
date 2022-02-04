package kr.co.nohorang.suryongfootprint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.nohorang.suryongfootprint.data.TestCount
import kr.co.nohorang.suryongfootprint.databinding.ActivityApprovalBinding

class ApprovalActivity : AppCompatActivity() {
    val binding by lazy { ActivityApprovalBinding.inflate(layoutInflater) }

    companion object {
        var current_id2: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val current_user_id=intent.getStringExtra("current_user_id")
        current_id2 = current_user_id

        // 테스트용 챌린지 데이터 생성
        val data:MutableList<TestCount> = loadTestCountData()
        // 어댑터 생성 후 데이터 저장
        var adapter = CountAdapter()
        adapter.listData = data
        // 어댑터 연결
        binding.recycler.adapter = adapter
        // 레이아웃 매니저 연결
        binding.recycler.layoutManager = LinearLayoutManager(this)

        // 승인 대기 아이템 - 액티비티 이동

        // 메인메뉴 버튼 - 액티비티 이동
        binding.homeMenuBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("current_user_id", current_user_id)
            startActivity(intent)
        }
        binding.rankingMenuBtn.setOnClickListener {
            val intent = Intent(this, RankingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("current_user_id", current_user_id)
            startActivity(intent)
        }
        binding.mypageMenuBtn.setOnClickListener {
            val intent = Intent(this, MypageMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("current_user_id", current_user_id)
            startActivity(intent)
        }
    }

    // 테스트용 참여횟수 데이터 생성 함수
    fun loadTestCountData(): MutableList<TestCount> {
        val data: MutableList<TestCount> = mutableListOf()

        for (no in 1..100) {
            val count_id = no
            val user_id = "아이디${no}"
            val challenge_id = no
            val challenge_count = 1
            val post_count = 1
            val approval_count = 2
            var testCount = TestCount(count_id, user_id, challenge_id, challenge_count, post_count, approval_count)
            data.add(testCount)
        }
        return data
    }
}