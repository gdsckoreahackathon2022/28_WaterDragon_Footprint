package kr.co.nohorang.suryongfootprint.data

data class TestCount(
    var count_id: Int,
    var user_id: String,
    var challenge_id: Int,
    var challenge_count: Int,
    var post_count: Int,
    var approval_count: Int
)
