package kr.co.nohorang.suryongfootprint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.co.nohorang.suryongfootprint.MypageMainActivity
import kr.co.nohorang.suryongfootprint.databinding.ActivityMainBinding
import kr.co.nohorang.suryongfootprint.databinding.ActivityRankingBinding

class RankingActivity : AppCompatActivity() {
    val binding by lazy { ActivityRankingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val current_user_id=intent.getStringExtra("current_user_id")

        // 메인메뉴 버튼 - 액티비티 이동 (+ 마이페이지 액티비티)
        binding.approvalMenuBtn.setOnClickListener {
            val intent = Intent(this, ApprovalActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("current_user_id", current_user_id)
            startActivity(intent)
        }
        binding.homeMenuBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
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
        //랭킹 data 받아서 띄우기
        /*binding.rankingnickname1.setText()
        binding.rankingCount1.setText()

        binding.rankingnickname2.setText()
        binding.rankingCount2.setText()

        binding.rankingnickname3.setText()
        binding.rankingCount3.setText()

        binding.rankingnickname4.setText()
        binding.rankingCount4.setText()

        binding.rankingnickname5.setText()
        binding.rankingCount5.setText()*/

        //내 랭킹 띄우기
        var myOrder:String= "0"
        var myPercent: String="0"

        binding.rankingMy.setText("내 순위: "+myOrder+"위 | 상위 "+myPercent+"% 발자국이에요!")



        binding.rankingUnit1.setOnClickListener{
            //닉네임1 회원 메달 페이지로 이동
        }
        binding.rankingUnit2.setOnClickListener{
            //닉네임1 회원 메달 페이지로 이동
        }
        binding.rankingUnit3.setOnClickListener{
            //닉네임1 회원 메달 페이지로 이동
        }
        binding.rankingUnit4.setOnClickListener{
            //닉네임1 회원 메달 페이지로 이동
        }
        binding.rankingUnit5.setOnClickListener{
            //닉네임1 회원 메달 페이지로 이동
        }

    }
}