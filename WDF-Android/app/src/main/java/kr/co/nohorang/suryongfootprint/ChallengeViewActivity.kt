package kr.co.nohorang.suryongfootprint

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View.inflate
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kr.co.nohorang.suryongfootprint.data.Challenge
import kr.co.nohorang.suryongfootprint.databinding.ActivityApprovalBinding.inflate
import kr.co.nohorang.suryongfootprint.databinding.ActivityChallengeViewBinding
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChallengeViewActivity : AppCompatActivity() {
    val data: MutableList<Challenge> = mutableListOf()

    var current_user_id: String? = null
    companion object {
        var current_id: String? = null
    }

    val binding by lazy { ActivityChallengeViewBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        current_user_id = intent.getStringExtra("current_user_id").toString()
        current_id = current_user_id

        // 종료 버튼 앞으로 이동
        binding.challengeViewExitBtn.bringToFront()

        // 종료 버튼 - 액티비티 종료
        binding.challengeViewExitBtn.setOnClickListener {
            finish()
        }

        // 챌린지 데이터 가져오기
        loadChallengeData()
    }

    // 챌린지 데이터 받아오기
    fun loadChallengeData() {
        // 챌린지 정보 받아오기
        //Retrofit 통신 - getChallenges
        RetrofitBuilder.challenge_api.getChallenges().enqueue(object :
            Callback<List<Challenge>> {
            //request, response 정상 수행
            override fun onResponse(
                call: Call<List<Challenge>>,
                response: Response<List<Challenge>>
            ) {
                // 전체 챌린지 받아오기
                var all_challenges: List<Challenge>? = arrayListOf<Challenge>()
                all_challenges = response.body()

                if (all_challenges != null) {
                    for (challenge in all_challenges) {
                        // 챌린지 속성값 받아오기
                        val challengeId = challenge.challenge_id
                        val title = challenge.title
                        val info = challenge.info
                        val condition = challenge.condition
                        val participants = challenge.participants
                        Log.d("GET_CH_T", "값 대입")
                        data.add(Challenge(challengeId, title, info, condition, participants))

                        // 로그 테스트
                        Log.d("GET_CH_T", "response : " + challenge.toString())
                        Log.d("GET_CH_T", " challenge_id : " + challengeId.toString())
                        Log.d("GET_CH_T", " title : " + title)
                        Log.d("GET_CH_T", " info : " + info)
                        Log.d("GET_CH_T", " condition : " + condition.toString())
                        Log.d("GET_CH_T", " participants : " + participants.toString())
                    }
                    data.sortBy { it.title }
                    // 어댑터 생성 후 데이터 저장
                    var adapter = ChallengeAdapter()
                    adapter.listData = data
                    // 어댑터 연결
                    binding.recycler.adapter = adapter
                    // 레이아웃 매니저 연결
                    binding.recycler.layoutManager =
                        GridLayoutManager(this@ChallengeViewActivity, 2)
                } else {
                    Toast.makeText(
                        this@ChallengeViewActivity,
                        "저장된 챌린지가 없습니다.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            //request, response 실패
            override fun onFailure(call: Call<List<Challenge>>, t: Throwable) {
                t.message?.let {
                    Log.e("GET_CH_F", it)
                    Log.d("GET_CH_T", "챌린지 정보 가져오기 실패")
                    Toast.makeText(
                        this@ChallengeViewActivity,
                        "오류가 발생하였습니다.",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                }
            }
        })
    }
}