package kr.co.nohorang.suryongfootprint

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kr.co.nohorang.suryongfootprint.data.*
import kr.co.nohorang.suryongfootprint.databinding.ActivityTest2Binding
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Test2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 전체 챌린지 받아오기 테스트
        binding.getChallengeBtn.setOnClickListener {
            //Retrofit 통신 - getChallenges
            RetrofitBuilder.challenge_api.getChallenges()
                .enqueue(object : Callback<List<Challenge>> {
                    //request, response 정상 수행
                    override fun onResponse(
                        call: Call<List<Challenge>>,
                        response: Response<List<Challenge>>
                    ) {
                        // 리스트의 challengeNum 번째 객체 선택)
                        var challengeNum = 0

                        // 전체 챌린지 받아오기
                        var all_challenges: List<Challenge>? = arrayListOf<Challenge>()
                        all_challenges = response.body()
                        // 챌린지 속성값 받아오기
                        val challengeId = all_challenges?.get(challengeNum)?.challenge_id
                        val title = all_challenges?.get(challengeNum)?.title
                        val info = all_challenges?.get(challengeNum)?.info
                        val condition = all_challenges?.get(challengeNum)?.condition
                        val participants = all_challenges?.get(challengeNum)?.participants

                        // 로그 테스트
                        Log.d("GET_CH_T", "response : " + all_challenges?.toString())
                        Log.d("GET_CH_T", " challenge_id : " + challengeId.toString())
                        Log.d("GET_CH_T", " title : " + title)
                        Log.d("GET_CH_T", " info : " + info)
                        Log.d("GET_CH_T", " condition : " + condition.toString())
                        Log.d("GET_CH_T", " participants : " + participants.toString())
                    }

                    //request, response 실패
                    override fun onFailure(call: Call<List<Challenge>>, t: Throwable) {
                        t.message?.let {
                            Log.e("GET_CH_F", it)
                            Log.d("GET_CH_T", "챌린지 정보 가져오기 실패")
                        }
                    }
                })
        }

//        var realUri: Uri ?= null
//
//        // 챌린지 참여하기(포스트 올리기) 테스트
//        binding.uploadPostBtn.setOnClickListener {
//            //이미지 형식 Blob으로 되어있음. (data/Post.kt 참고)
//            var p_dto = PostCreationDTO("testID",chall, realUri,"챌린지 참여합니다~",1)
//
//            //response로 가져올 data 선언
//            var responsePost: Post?=null
//
//            //Retrofit 통신 - getChallenges
//            RetrofitBuilder.challenge_api.createPost(p_dto).enqueue(object : Callback<Post> {
//                //request, response 정상 수행
//                override fun onResponse(call: Call<Post>, response: Response<Post>) {
//                    //업로드한 Post 정보
//                    responsePost=response.body()
//                    Log.d("CREATE_POST_T", "response : " + responsePost?.toString())
//                    Log.d("CREATE_POST_T", "user_id : " + responsePost?.user_id)
//                    Log.d("CREATE_POST_T", "challenge_id : " + responsePost?.challenge_id.toString())
//                    Log.d("CREATE_POST_T", "img : " + responsePost?.img.toString())
//                    Log.d("CREATE_POST_T", "content : " + responsePost?.content)
//                    Log.d("CREATE_POST_T", "state : " + responsePost?.state.toString() )
//                }
//
//                //request, response 실패
//                override fun onFailure(call: Call<Post>, t: Throwable) {
//                    t.message?.let { Log.e("CREATE_POST_F", it) }
//                }
//            })
//        }

        // 참여횟수
        binding.getPostCountBtn.setOnClickListener {
            //Retrofit 통신 - getChallenges
            RetrofitBuilder.challenge_api.getPostCounts().enqueue(object : Callback<List<Count>> {
                //request, response 정상 수행
                override fun onResponse(
                    call: Call<List<Count>>,
                    response: Response<List<Count>>
                ) {
                    // 리스트의 challengeNum 번째 객체 선택)
                    var challengeNum = 0

                    // 전체 count 받아오기
                    var all_count: List<Count>? = arrayListOf<Count>()

//                    all_challenges = response.body()
//                    // 챌린지 속성값 받아오기
//                    val challengeId = all_challenges?.get(challengeNum)?.challenge_id
//                    val title = all_challenges?.get(challengeNum)?.title
//                    val info = all_challenges?.get(challengeNum)?.info
//                    val condition = all_challenges?.get(challengeNum)?.condition
//                    val participants = all_challenges?.get(challengeNum)?.participants

//                    // 로그 테스트
//                    Log.d("GET_CH_T", "response : " + all_challenges?.toString())
//                    Log.d("GET_CH_T", " challenge_id : " + challengeId.toString())
//                    Log.d("GET_CH_T", " title : " + title)
//                    Log.d("GET_CH_T", " info : " + info)
//                    Log.d("GET_CH_T", " condition : " + condition.toString())
//                    Log.d("GET_CH_T", " participants : " + participants.toString())
                }

                //request, response 실패
                override fun onFailure(call: Call<List<Count>>, t: Throwable) {
                    t.message?.let {
                        Log.e("GET_CH_F", it)
                        Log.d("GET_CH_T", "챌린지 정보 가져오기 실패")
                    }
                }
            })
        }

        //아이디 중복 조회
        binding.AvailableIdBtn.setOnClickListener {
            //input id 받아오기
            var input_id = "1234"

            //response로 가져올 data 선언
            var response_id: String? = null

            //Retrofit 통신
            RetrofitBuilder.api.existUserId(input_id).enqueue(object : Callback<String> {
                //request, response 정상 수행
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    response_id = response.body().toString()
                    Log.d("FIND_ID_T", "response : " + response.body().toString())
                    if (response_id == input_id) {//input 아이디와 같은 아이디가 존재
                        Log.d("FIND_ID_T", "status : 사용 불가능 아이디")
                    } else{
                        Log.d("FIND_ID_T", "status : 사용 가능 아이디")
                    }
                }

                //request, response 실패
                override fun onFailure(call: Call<String>, t: Throwable) {
                    t.message?.let { Log.e("FIND_ID_F", it) }
                }
            })



        }

        //이메일 중복 조회
        binding.AvailableEmailBtn.setOnClickListener {
            //input id 받아오기
            var input_email = "coco"

            //response로 가져올 data 선언
            var response_email: String? = null

            //Retrofit 통신
            RetrofitBuilder.api.existUserEmail(input_email).enqueue(object : Callback<String> {
                //request, response 정상 수행
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    response_email = response.body().toString()
                    Log.d("FIND_ID_T", "response : " + response.body().toString())
                    if (response_email == input_email) {
                        Log.d("FIND_ID_T", "status : 사용 불가능 이메일")
                    } else {
                        Log.d("FIND_ID_T", "status : 사용 가능 이메일")

                    }
                }

                //request, response 실패
                override fun onFailure(call: Call<String>, t: Throwable) {
                    t.message?.let { Log.e("FIND_ID_F", it) }
                }
            })

        }

        //닉네임 중복 조회
        binding.AvailableNickBtn.setOnClickListener {
            //input id 받아오기
            var input_nick = "coco"

            //response로 가져올 data 선언
            var response_nick: String? = null

            //Retrofit 통신
            RetrofitBuilder.api.existUserNickname(input_nick).enqueue(object : Callback<String> {
                //request, response 정상 수행
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    response_nick = response.body().toString()
                    Log.d("FIND_ID_T", "response : " + response.body().toString())
                    if (response_nick == input_nick) {
                        Log.d("FIND_ID_T", "status : 사용 불가능 닉네임")
                    } else  {
                        Log.d("FIND_ID_T", "status : 사용 가능 닉네임")
                    }
                }

                //request, response 실패
                override fun onFailure(call: Call<String>, t: Throwable) {
                    t.message?.let { Log.e("FIND_ID_F", it) }
                }
            })

        }

    }
}