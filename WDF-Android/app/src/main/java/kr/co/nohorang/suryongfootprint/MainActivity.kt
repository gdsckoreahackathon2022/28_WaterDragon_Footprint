package kr.co.nohorang.suryongfootprint

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kr.co.nohorang.suryongfootprint.data.Challenge
import kr.co.nohorang.suryongfootprint.databinding.ActivityMainBinding
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    val data: MutableList<Challenge> = mutableListOf()

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val current_user_id=intent.getStringExtra("current_user_id")

        // 오늘의 챌린지 텍스트 앞으로 이동
        binding.dailyChallengeText.bringToFront()
        // 오늘의 챌린지 버튼 - 액티비티 이동
        binding.dailyChallengeBtn.setOnClickListener {
            val intent = Intent(this, ChallengeActivity::class.java)
            intent.putExtra("current_user_id", current_user_id)
            startActivity(intent)
        }

        // 다른 챌린지 보기 버튼 - 액티비티 이동
        binding.viewMoreChallengeBtn.setOnClickListener {
            val intent = Intent(this, ChallengeViewActivity::class.java)
            intent.putExtra("current_user_id", current_user_id)
            startActivity(intent)
        }

        // 메인메뉴 버튼 - 액티비티 이동 (+ 마이페이지 액티비티)
        binding.approvalMenuBtn.setOnClickListener {
            val intent = Intent(this, ApprovalActivity::class.java)
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
                        val title = challenge.title?.replace(" ", "\n")
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
                    data.sortBy { it.participants }
                    data.reverse()
                    Log.d("GET_CH_T", "정렬된 리스트 : " + data)
                    binding.mainChallengeBtn1.text = data[0].title
                    binding.mainChallengeBtn2.text = data[1].title
                    binding.mainChallengeBtn3.text = data[2].title
                    binding.mainChallengeBtn4.text = data[3].title
                    binding.mainChallengeBtn5.text = data[4].title

                    val challengeCount = data.size
                    val calendar: Calendar = Calendar.getInstance()
                    val today = calendar.get(Calendar.DAY_OF_MONTH)
                    val daily = today % challengeCount
                    binding.dailyChallengeText.text = data[daily].title?.replace("\n", " ") + "!\n함께 참여해볼까요?"
                    binding.dailyChallengeBtn.setOnClickListener {
                        val intent = Intent(this@MainActivity, ChallengeActivity::class.java)
                        intent.putExtra("challenge", data[daily].challenge_id)
                        intent.putExtra("current_user_id", current_user_id)
                        startActivity(intent)
                    }

                    // 인기순 챌린지 버튼(1위 ~ 5위) - 액티비티 이동
                    binding.mainChallengeBtn1.setOnClickListener {
                        val intent = Intent(this@MainActivity, ChallengeActivity::class.java)
                        intent.putExtra("challenge", data[0].challenge_id)
                        intent.putExtra("current_user_id", current_user_id)
                        startActivity(intent)
                    }
                    binding.mainChallengeBtn2.setOnClickListener {
                        val intent = Intent(this@MainActivity, ChallengeActivity::class.java)
                        intent.putExtra("challenge", data[1].challenge_id)
                        intent.putExtra("current_user_id", current_user_id)
                        startActivity(intent)
                    }
                    binding.mainChallengeBtn3.setOnClickListener {
                        val intent = Intent(this@MainActivity, ChallengeActivity::class.java)
                        intent.putExtra("challenge", data[2].challenge_id)
                        intent.putExtra("current_user_id", current_user_id)
                        startActivity(intent)
                    }
                    binding.mainChallengeBtn4.setOnClickListener {
                        val intent = Intent(this@MainActivity, ChallengeActivity::class.java)
                        intent.putExtra("challenge", data[3].challenge_id)
                        intent.putExtra("current_user_id", current_user_id)
                        startActivity(intent)
                    }
                    binding.mainChallengeBtn5.setOnClickListener {
                        val intent = Intent(this@MainActivity, ChallengeActivity::class.java)
                        intent.putExtra("challenge", data[4].challenge_id)
                        intent.putExtra("current_user_id", current_user_id)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
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
                        this@MainActivity,
                        "오류가 발생하였습니다.",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                }
            }
        })
    }
}