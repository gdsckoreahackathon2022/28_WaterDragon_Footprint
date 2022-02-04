package com.nohorang.suryongfootprint.controller;

import com.nohorang.suryongfootprint.model.Challenge;
import com.nohorang.suryongfootprint.model.Count;
import com.nohorang.suryongfootprint.model.Post;
import com.nohorang.suryongfootprint.model.User;
import com.nohorang.suryongfootprint.model.request.PostCreationRequest;
import com.nohorang.suryongfootprint.model.request.PostUpdateRequest;
import com.nohorang.suryongfootprint.model.request.UserCreationRequest;
import com.nohorang.suryongfootprint.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/s-footprint/challenge")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChallengeController {
    private final ChallengeService challengeService;

    //전체 챌린지 가져오기
    @GetMapping("")
    public ResponseEntity<List<Challenge>> readChallenges () {
        return ResponseEntity.ok(challengeService.readChallenges());
    }

    //챌린지 아이디로 가져오기
    @GetMapping("/{challenge_id}")
    public ResponseEntity<Challenge> getChallenge (@PathVariable int challenge_id) {
        return ResponseEntity.ok(challengeService.getChallenge(challenge_id));
    }

    //챌린지 참여 - 포스트 올림
    @PostMapping("/post")
    public ResponseEntity<Post> createPost(@RequestBody PostCreationRequest postCreationRequest){
        return ResponseEntity.ok(challengeService.createPost(postCreationRequest));
    }

    // 전체 COUNT 가져오기
    @GetMapping("/count")
    public ResponseEntity<List<Count>> readCounts () {
        return ResponseEntity.ok(challengeService.readCounts());
    }

    //사용자별 포스트 가져오기
    @GetMapping("/post/{user_id}")
    public ResponseEntity<List<Post>> getUserPosts (@PathVariable String user_id) {
        return ResponseEntity.ok(challengeService.getUserPosts(user_id));
    }

    //챌린지별 참여(Post) 조회
    @GetMapping("/post/chall/{challenge_id}")
    public ResponseEntity<List<Post>> getChallengePosts (@PathVariable int challenge_id) {
        return ResponseEntity.ok(challengeService.getChallengePosts(challenge_id));
    }

    //사용자별 챌린지별 참여(Post) 조회
    @GetMapping("/post/{user_id}/chall/{challenge_id}")
    public ResponseEntity<List<Post>> getUserChallengePosts (@PathVariable String user_id, @PathVariable int challenge_id) {
        return ResponseEntity.ok(challengeService.getUserChallengePosts(user_id,challenge_id));
    }

    //사용자별 챌린지별 상태별 참여(Post) 조회
    @GetMapping("/post/{user_id}/chall/{challenge_id}/{state}")
    public ResponseEntity<List<Post>> getUserChallengeStatePosts (@PathVariable String user_id, @PathVariable int challenge_id,@PathVariable int state) {
        return ResponseEntity.ok(challengeService.getUserChallengeStatePosts(user_id,challenge_id,state));
    }

    //참여(Post) 수정
    //PostCreationRequest에서 content값만 사용 -> 나머지는 빈 값이어도 됨.
    @PatchMapping("/post/{post_id}/update")
    public ResponseEntity<Post> updateUserNickname(@PathVariable int post_id, @RequestBody PostUpdateRequest request){
        return ResponseEntity.ok(challengeService.updatePostContent(post_id, request));
    }

    //참여(Post) 삭제
    @DeleteMapping("/post/{post_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int post_id){
        challengeService.deletePost(post_id);
        return ResponseEntity.ok().build();
    }

}
