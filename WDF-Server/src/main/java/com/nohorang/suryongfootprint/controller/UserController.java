package com.nohorang.suryongfootprint.controller;

import com.nohorang.suryongfootprint.model.User;
import com.nohorang.suryongfootprint.model.request.UserCreationRequest;
import com.nohorang.suryongfootprint.model.request.UserLoginRequest;
import com.nohorang.suryongfootprint.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/s-footprint/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody UserCreationRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<User> getUserLogin(@RequestBody UserLoginRequest request){
        return ResponseEntity.ok(userService.getUserLogin(request));
    }

    //아이디 찾기
    @GetMapping("/user_id")
    public ResponseEntity<String> findUserId(@RequestParam String user_name,@RequestParam String user_email){
        return ResponseEntity.ok(userService.findUserId(user_name,user_email));
    }

//    //비밀번호 찾기
    @GetMapping("/user_pw")
    public ResponseEntity<String> findUserPW(@RequestParam String user_name, @RequestParam String user_id, @RequestParam String user_email){
        return ResponseEntity.ok(userService.findUserPW(user_name, user_id, user_email));
    }

    //아이디 중복확인
    @GetMapping("/available/id")
    public ResponseEntity<String> existUserId(@RequestParam String user_id){
        return ResponseEntity.ok(userService.existUserId(user_id));
    }

    //이메일 중복확인
    @GetMapping("/available/email")
    public ResponseEntity<String> existUserEmail(@RequestParam String user_email){
        return ResponseEntity.ok(userService.existUserEmail(user_email));
    }

    //닉네임 중복확인
    @GetMapping("/available/nickname")
    public ResponseEntity<String> existUserNickName(@RequestParam String user_nickname){
        return ResponseEntity.ok(userService.existUserNickName(user_nickname));
    }

    //비밀번호 수정
    //UserCrearionRequest에서 user_pw값만 사용 -> 나머지는 빈 값이어도 됨.
    @PatchMapping("/{user_id}/pw")
    public ResponseEntity<User> updateUserPW(@PathVariable("user_id") String user_id, @RequestBody UserCreationRequest request){
        return ResponseEntity.ok(userService.updateUserPW(user_id, request));
    }

    //닉네임 수정
    //UserCrearionRequest에서 user_nickname값만 사용 -> 나머지는 빈 값이어도 됨.
    @PatchMapping("/{user_id}/nickname")
    public ResponseEntity<User> updateUserNickname(@PathVariable("user_id") String user_id, @RequestBody UserCreationRequest request){
        return ResponseEntity.ok(userService.updateUserNickName(user_id, request));
    }

    //회원 탈퇴
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String user_id){
        userService.deleteUser(user_id);
        return ResponseEntity.ok().build();
    }


}
