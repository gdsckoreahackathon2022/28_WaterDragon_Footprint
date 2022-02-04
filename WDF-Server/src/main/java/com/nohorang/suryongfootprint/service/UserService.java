package com.nohorang.suryongfootprint.service;

import com.nohorang.suryongfootprint.model.User;
import com.nohorang.suryongfootprint.model.request.UserCreationRequest;
import com.nohorang.suryongfootprint.model.request.UserLoginRequest;
import com.nohorang.suryongfootprint.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //회원가입
    public User createUser(UserCreationRequest request){
        User user = new User();
        BeanUtils.copyProperties(request, user);
        return userRepository.save(user);
    }

    //로그인
    public User getUserLogin(UserLoginRequest loginRequest){
        Optional<User> user = userRepository.findById(loginRequest.getUserId());
        if(user.isPresent()){
            if(user.get().getUserPw().equals(loginRequest.getUserPw())) {
                return user.get();
            }
        }
        //로그인 실패
        throw new EntityNotFoundException("Cant find any user under given ID and PW");
    }

    //아이디 찾기
    public String findUserId(String user_name,String user_email){
        List<User> user = userRepository.findByUserNameAndUserEmail(user_name,user_email);
        if(user.get(0)!=null){
            return user.get(0).getUserId();
        }
        //아이디 찾기 실패
        throw new EntityNotFoundException("Cant find any user given Info");
    }

    //비밀번호 찾기
    public String findUserPW( String user_name, String user_id,String user_email){
        Optional<User> user = userRepository.findByUserIdAndUserNameAndUserEmail(user_id,user_name,user_email);
        String pw_str = "";
        if(user.isPresent()){
            pw_str = user.get().getUserPw();
            return pw_str.substring(0,2)+"*".repeat(pw_str.length()-2);
        }
        //비밀번호 찾기 실패
        throw new EntityNotFoundException("Cant find any user given Info");
    }

    //비밀번호 변경
    public User updateUserPW(String user_id, UserCreationRequest request){
        Optional<User> user = userRepository.findById(user_id);
        if (!user.isPresent()) {
            throw new EntityNotFoundException("User Not Found");
        }
        User c_user = user.get();
        c_user.setUserPw(request.getUserPw());
        return userRepository.save(c_user);
    }

    //닉네임 변경
    public User updateUserNickName(String user_id, UserCreationRequest request){
        Optional<User> user = userRepository.findById(user_id);
        if (!user.isPresent()) {
            throw new EntityNotFoundException("User Not Found");
        }
        User c_user = user.get();
        c_user.setUserNickname(request.getUserNickname());
        return userRepository.save(c_user);
    }

    //회원 탈퇴
    public void deleteUser(String user_id){
        userRepository.deleteById(user_id);
    }

    //아이디 중복 조회
    public String existUserId(String user_id){
        Optional<User> user = userRepository.findById(user_id);
        if (user.isPresent()) {
            return user.get().getUserId();
        }
        throw new EntityNotFoundException("Cant find any user given Info");
    }

    //이메일 중복 조회
    public String existUserEmail(String user_email){
        Optional<User> user = userRepository.findByUserEmail(user_email);
        if (user.isPresent()) {
            return user.get().getUserEmail();
        }
        throw new EntityNotFoundException("Cant find any user given Info");
    }

    //닉네임 중복 조회
    public String existUserNickName(String user_nickname){
        Optional<User> user = userRepository.findByUserNickname(user_nickname);
        if (user.isPresent()) {
            return user.get().getUserNickname();
        }
        throw new EntityNotFoundException("Cant find any user given Info");
    }

}
