package kr.co.nohorang.suryongfootprint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.nohorang.suryongfootprint.data.TestPost
import kr.co.nohorang.suryongfootprint.databinding.ListitemPostBinding
import java.text.SimpleDateFormat

class PostAdapter: RecyclerView.Adapter<PostHolder>() {
    // 실제 데이터로 변환 필요
    var listData = mutableListOf<TestPost>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = ListitemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(postHolder: PostHolder, position: Int) {
        val count = listData.get(position)
        postHolder.setCount(count)
    }
}

class PostHolder(val binding: ListitemPostBinding): RecyclerView.ViewHolder(binding.root) {
    // testCount 대신 실제 데이터로 변환 필요 (+ challenge 데이터, user 데이터, post 데이터)
    fun setCount(testPost: TestPost) {
        // binding.postImg.setImageURI(testPost.img)

        var sdf = SimpleDateFormat("yyyy.MM.dd.")
        var formattedDate = sdf.format(testPost.date)
        binding.postDateText.text = formattedDate

        binding.postContentText.text = testPost.content
    }
}