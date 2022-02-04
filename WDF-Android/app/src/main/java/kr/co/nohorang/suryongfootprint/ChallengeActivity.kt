package kr.co.nohorang.suryongfootprint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import kr.co.nohorang.suryongfootprint.databinding.ActivityChallengeBinding
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import okhttp3.Challenge
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChallengeActivity : AppCompatActivity() {
    val binding by lazy { ActivityChallengeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val current_user_id=intent.getStringExtra("current_user_id")
        // 툴바 뒤로가기 버튼 - 액티비티 종료
        binding.backBtn.setOnClickListener {
            finish()
        }

        // 이전 액티비티에서 챌린지 아이디 전달 받기
        val challengeIntent = getIntent()
        var challengeID = challengeIntent.getIntExtra("challenge", -1)
        Log.d("CHALLENGE_ID", challengeID.toString())

        // 챌린지 정보 받아오기
        //Retrofit 통신 - getChallenges
        RetrofitBuilder.challenge_api.getChallenges().enqueue(object :
            Callback<List<kr.co.nohorang.suryongfootprint.data.Challenge>> {
            //request, response 정상 수행
            override fun onResponse(
                call: Call<List<kr.co.nohorang.suryongfootprint.data.Challenge>>,
                response: Response<List<kr.co.nohorang.suryongfootprint.data.Challenge>>
            ) {
                // 전체 챌린지 받아오기
                var all_challenges: List<kr.co.nohorang.suryongfootprint.data.Challenge>? = arrayListOf<kr.co.nohorang.suryongfootprint.data.Challenge>()
                all_challenges = response.body()

                if (all_challenges != null) {
                    var selected = null
                    for (challenge in all_challenges) {
                        if (challenge.challenge_id == challengeID) {
                            binding.challengeTitleText.text = challenge.title
                            binding.challengeInfoText.text = challenge.info + "\n\n - 달성 기준 : ${challenge.condition}회 인증\n - 참여인원 : ${challenge.participants}"
                            // 확인 버튼 - 액티비티 이동
                            binding.postBtn.setOnClickListener {
                                val intent = Intent(this@ChallengeActivity, PostActivity::class.java)
                                intent.putExtra("challenge", challenge.challenge_id)
                                intent.putExtra("current_user_id", current_user_id)
                                startActivity(intent)
                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        this@ChallengeActivity,
                        "저장된 챌린지가 없습니다.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            //request, response 실패
            override fun onFailure(call: Call<List<kr.co.nohorang.suryongfootprint.data.Challenge>>, t: Throwable) {
                t.message?.let {
                    Log.e("GET_CH_F", it)
                    Log.d("GET_CH_T", "챌린지 정보 가져오기 실패")
                    Toast.makeText(
                        this@ChallengeActivity,
                        "오류가 발생하였습니다.",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                }
            }
        })
    }
}