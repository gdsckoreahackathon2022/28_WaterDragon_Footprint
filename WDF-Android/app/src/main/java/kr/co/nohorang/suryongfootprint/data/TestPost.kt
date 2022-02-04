package kr.co.nohorang.suryongfootprint.data

import android.net.Uri
import android.text.Html

data class TestPost(
    var post_id: Int,
    var user_id: String,
    var challenge_id: Int,
    var count_id: Int,
    // var img: Uri,
    var content: String,
    var state: Int,
    var date: Long
)
