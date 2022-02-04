package kr.co.nohorang.suryongfootprint

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import kr.co.nohorang.suryongfootprint.databinding.ActivityPostBinding
import java.io.IOException
import java.text.SimpleDateFormat
import android.content.DialogInterface
import android.graphics.Color
import kr.co.nohorang.suryongfootprint.data.Post
import kr.co.nohorang.suryongfootprint.data.PostCreationDTO
import kr.co.nohorang.suryongfootprint.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.sql.Blob


class PostActivity : BaseActivity() {
    val PERM_STORAGE = 99
    val PERM_CAMERA = 100
    val REQ_CAMERA = 101
    val REQ_STORAGE = 102

    val binding by lazy { ActivityPostBinding.inflate(layoutInflater) }

    var realUri: Uri? = null

    // 데이터베이스에 저장될 Blob 변수
    var postImg: Blob? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val current_user_id=intent.getStringExtra("current_user_id")

        // 이전 액티비티에서 챌린지 아이디 전달 받기
        val challengeIntent = getIntent()
        var challengeID = challengeIntent.getIntExtra("challenge", -1)
        Log.d("CHALLENGE_ID", challengeID.toString())

        // 툴바 뒤로가기 버튼 - 액티비티 종료
        binding.backBtn.setOnClickListener {
            finish()
        }

        // EditText 입력 중 외부 터치 시 키보드 내리기
        binding.layout.setOnClickListener {
            hideKeyboard(binding.postContentEditText)
        }
        binding.linear.setOnClickListener {
            hideKeyboard(binding.postContentEditText)
        }

        // 인증 버튼 - 액티비티 종료 (+ DB에 저장)
        binding.postBtn.setOnClickListener {
            //이미지 형식 Blob으로 되어있음. (data/Post.kt 참고)
            val user_id = "testID"
            val challenge_id = challengeID
            val img = postImg
            val content = binding.postContentEditText.text.toString()
            val state = 0

            var p_dto = PostCreationDTO(user_id, challenge_id, img, content, state)

            //response로 가져올 data 선언
            var responsePost: Post?=null

            //Retrofit 통신 - getChallenges
            RetrofitBuilder.challenge_api.createPost(p_dto).enqueue(object : Callback<Post> {
                //request, response 정상 수행
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    //업로드한 Post 정보
                    responsePost=response.body()
                    Log.d("CREATE_POST_T", "response : " + responsePost?.toString())
                    Log.d("CREATE_POST_T", "user_id : " + responsePost?.user_id)
                    Log.d("CREATE_POST_T", "challenge_id : " + responsePost?.challenge_id.toString())
                    Log.d("CREATE_POST_T", "img : " + responsePost?.img.toString())
                    Log.d("CREATE_POST_T", "content : " + responsePost?.content)
                    Log.d("CREATE_POST_T", "state : " + responsePost?.state.toString() )

                    Toast.makeText(this@PostActivity, "인증이 업로드되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }

                //request, response 실패
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    t.message?.let { Log.e("CREATE_POST_F", it)
                    Toast.makeText(this@PostActivity, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        binding.imagePreview.setOnClickListener {
            // 갤러리 또는 카메라 선택
            val items = arrayOf<CharSequence>("카메라", "갤러리")
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)

            // 알림창 제목 및 선택지 설정
            alertDialogBuilder.setTitle("인증 사진 첨부 방법")
            alertDialogBuilder.setItems(items,
                DialogInterface.OnClickListener { dialog, id ->
                    when (items[id].toString()) {
                        "카메라" -> requirePermissions(
                            arrayOf(Manifest.permission.CAMERA),
                            PERM_CAMERA
                        )
                        "갤러리" -> requirePermissions(
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            PERM_STORAGE
                        )
                    }
                    dialog.dismiss()
                })

            // 다이얼로그 생성
            val alertDialog: AlertDialog = alertDialogBuilder.create()

            // 다이얼로그 보여주기
            alertDialog.show()
        }
    }

    // 권한 허용 선택
    override fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> openGallery()
            PERM_CAMERA -> openCamera()
        }
    }

    // 권한 거부 선택
    override fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> Toast.makeText(
                baseContext,
                "갤러리 접근 권한을 승인해야 인증할 수 있습니다.",
                Toast.LENGTH_SHORT
            ).show()
            PERM_CAMERA -> Toast.makeText(
                baseContext,
                "카메라 권한을 승인해야 인증할 수 있습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // 갤러리 실행
    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQ_STORAGE)
    }

    // 카메라 실행
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        createImageUri(newFileName(), "image/jpg")?.let { uri ->
            realUri = uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            startActivityForResult(intent, REQ_CAMERA)
        }
    }

    fun createImageUri(filename: String, mimeType: String): Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd__HHmmSS")
        val filename = sdf.format(System.currentTimeMillis())
        return "$filename.jpg"
    }

    fun loadBitmap(photoUri: Uri): Bitmap? {
        var image: Bitmap? = null
        try {
            image = if (Build.VERSION.SDK_INT > 27) {
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(this.contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQ_CAMERA -> {
                    realUri?.let { uri ->
                        val bitmap = loadBitmap(uri)
                        binding.imagePreview.setImageBitmap(bitmap)

//                        // 비트맵 BLOB 형식으로 변환
//                        val stream = ByteArrayOutputStream()
//                        if (bitmap != null) {
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                        }
//                        postImg = stream.toByteArray()
//                        Log.d("CREATE_POST_T", "img값 : " + postImg.toString())
//
//                        realUri = null
                    }
                }
                REQ_STORAGE -> {
                    data?.data?.let { uri ->
                        binding.imagePreview.setImageURI(uri)
//                        postImg = uri
                    }
                }
            }
            binding.postBtn.setBackgroundColor(Color.parseColor("#ACC236"))
            binding.postBtn.isEnabled = true
        }
    }



    // 키보드 비활성화 함수
    fun hideKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}