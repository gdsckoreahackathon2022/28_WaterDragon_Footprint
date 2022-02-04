package kr.co.nohorang.suryongfootprint

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kr.co.nohorang.suryongfootprint.ApprovalActivity.Companion.current_id2
import kr.co.nohorang.suryongfootprint.data.TestCount
import kr.co.nohorang.suryongfootprint.databinding.ListitemApprovalBinding

class CountAdapter: RecyclerView.Adapter<CountHolder>() {
    // 실제 데이터로 변환 필요
    var listData = mutableListOf<TestCount>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountHolder {
        val binding = ListitemApprovalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(countHolder: CountHolder, position: Int) {
        val count = listData.get(position)
        countHolder.setCount(count)
    }
}

class CountHolder(val binding: ListitemApprovalBinding): RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context, ApprovalDetailActivity::class.java)
            intent.putExtra("current_user_id", current_id2)
            startActivity(binding.root.context, intent, null)
        }
    }

    // testCount 대신 실제 데이터로 변환 필요 (+ challenge 데이터, user 데이터, post 데이터)
    fun setCount(testCount: TestCount) {
        // 사진 - post 데이터 사진 필요
        // binding.postImg.setImageURI()

        // 제목 - challenge 데이터 필요
        binding.titleText.text = "챌린지${testCount.challenge_id} 도전"

        // 닉네임 - user 데이터 필요
        binding.approvalNicknameText.text = "닉네임${testCount.count_id}"

        // 내용 - post 데이터 중 최신 글 필요
        binding.approvalContentText.text = "챌린지${testCount.challenge_id} ${testCount.post_count}번 참여합니다!"
    }
}