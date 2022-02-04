package com.nohorang.suryongfootprint.service;

import com.nohorang.suryongfootprint.model.Challenge;
import com.nohorang.suryongfootprint.model.Count;
import com.nohorang.suryongfootprint.model.Post;
import com.nohorang.suryongfootprint.model.User;
import com.nohorang.suryongfootprint.model.request.PostCreationRequest;
import com.nohorang.suryongfootprint.model.request.PostUpdateRequest;
import com.nohorang.suryongfootprint.model.request.UserCreationRequest;
import com.nohorang.suryongfootprint.repository.ChallengeRepository;
import com.nohorang.suryongfootprint.repository.CountRepository;
import com.nohorang.suryongfootprint.repository.PostRepository;
import com.nohorang.suryongfootprint.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final PostRepository postRepository;
    private final CountRepository countRepository;

    //전체 챌린지 읽어오기
    public List<Challenge> readChallenges() {
        return challengeRepository.findAll();
    }

    //전체 참여횟수 읽어오기
    public List<Count> readCounts() {
        return countRepository.findAll();
    }

    //전체 챌린지 읽어오기 -> 참여자 수 내림차순으로 가져올 수 있는 방법?

    //챌린지 id로 읽어오기
    public Challenge getChallenge(int user_id){
        Optional<Challenge> challenge= challengeRepository.findById(user_id);
        if(challenge.isPresent()){
            return challenge.get();
        }
        throw new EntityNotFoundException("Cant find any challenge under given ID");
    }

    //참여 생성
    public Post createPost(PostCreationRequest request) {
        Optional<User> userForId = userRepository.findById(request.getUserId());
        if (!userForId.isPresent()) {
            throw new EntityNotFoundException("User not present in the database");
        }
        User user = userForId.get();

        Optional<Challenge> challengeForId = challengeRepository.findById(request.getChallengeId());
        if (!challengeForId.isPresent()) {
            throw new EntityNotFoundException("Challenge not present in the database");
        }
        Challenge challenge = challengeForId.get();

        //Count 참여 횟수 테이블
        Optional<Count> countForId = countRepository.findByUserAndChallenge(user,challenge);
        Count new_count = new Count();
        if (!countForId.isPresent()) {
            new_count.setUser(user);
            new_count.setChallenge(challenge);
            new_count.setChallengeCount(1);
            new_count.setPostCount(1);
            new_count.setApprovalCount(2);
            countRepository.save(new_count);
        }else{
            BeanUtils.copyProperties(countForId.get(),new_count);
            int p_cnt=new_count.getPostCount();
            new_count.setPostCount(p_cnt);
            countRepository.save(new_count);
        }

        Post new_post = new Post();
        new_post.setUser(user);
        new_post.setChallenge(challenge);
        new_post.setCount(new_count);
        new_post.setContent(request.getContent());
        new_post.setDate(LocalDateTime.now());
        new_post.setImg(request.getImg());
        new_post.setState(request.getState());

        return postRepository.save(new_post);
    }

    //사용자별 참여(Post) 조회
    public List<Post> getUserPosts(String user_id){
        Optional<User> userForId = userRepository.findById(user_id);
        if (!userForId.isPresent()) {
            throw new EntityNotFoundException("User not present in the database");
        }
        User user = userForId.get();

        List<Post> user_posts= postRepository.findByUser(user);
        if(user_posts.isEmpty()){
            throw new EntityNotFoundException("Cant find any post under given ID");
        }
        return user_posts;
    }

    //챌린지별 참여(Post) 조회
    public List<Post> getChallengePosts(int challenge_id){
        Optional<Challenge> challengeForId = challengeRepository.findById(challenge_id);
        if (!challengeForId.isPresent()) {
            throw new EntityNotFoundException("User not present in the database");
        }
        Challenge challenge = challengeForId.get();

        List<Post> challenge_posts= postRepository.findByChallenge(challenge);
        if(challenge_posts.isEmpty()){
            throw new EntityNotFoundException("Cant find any post under given ID");
        }
        return challenge_posts;
    }

    //사용자별 챌린지별 참여(Post) 조회
    public List<Post> getUserChallengePosts(String user_id, int challenge_id){
        //사용자 정보 가져오기
        Optional<User> userForId = userRepository.findById(user_id);
        if (!userForId.isPresent()) {
            throw new EntityNotFoundException("User not present in the database");
        }
        User user = userForId.get();

        //챌린지 정보 가져오기
        Optional<Challenge> challengeForId = challengeRepository.findById(challenge_id);
        if (!challengeForId.isPresent()) {
            throw new EntityNotFoundException("User not present in the database");
        }
        Challenge challenge = challengeForId.get();

        //post 쿼리
        List<Post> user_challenge_posts= postRepository.findByUserAndChallenge(user,challenge);
        if(user_challenge_posts.isEmpty()){
            throw new EntityNotFoundException("Cant find any post under given ID");
        }
        return user_challenge_posts;
    }

    //사용자별 챌린지별 상태별 참여(Post) 조회
    public List<Post> getUserChallengeStatePosts(String user_id, int challenge_id, int state){
        //사용자 정보 가져오기
        Optional<User> userForId = userRepository.findById(user_id);
        if (!userForId.isPresent()) {
            throw new EntityNotFoundException("User not present in the database");
        }
        User user = userForId.get();

        //챌린지 정보 가져오기
        Optional<Challenge> challengeForId = challengeRepository.findById(challenge_id);
        if (!challengeForId.isPresent()) {
            throw new EntityNotFoundException("User not present in the database");
        }
        Challenge challenge = challengeForId.get();

        //post 쿼리
        List<Post> user_challenge_s_posts= postRepository.findByUserAndChallengeAndState(user,challenge,state);
        if(user_challenge_s_posts.isEmpty()){
            throw new EntityNotFoundException("Cant find any post under given ID");
        }
        return user_challenge_s_posts;
    }

    //참여(Post) 수정
    public Post updatePostContent(int post_id, PostUpdateRequest request){
        Optional<Post> post = postRepository.findById(post_id);
        if (!post.isPresent()) {
            throw new EntityNotFoundException("User Not Found");
        }
        Post c_post = post.get();

        c_post.setContent(request.getContent());
        return postRepository.save(c_post);
    }


    //참여(Post) 삭제
    public void deletePost(int post_id){

        postRepository.deleteById(post_id);
    }

}
