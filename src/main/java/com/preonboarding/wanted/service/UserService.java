package com.preonboarding.wanted.service;

import com.preonboarding.wanted.dto.request.SignUpPostRequest;
import com.preonboarding.wanted.dto.response.SignUpPostResponse;
import com.preonboarding.wanted.entity.User;
import com.preonboarding.wanted.repository.UserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpPostResponse signUp(SignUpPostRequest requestDto) {

        Optional<User> userEmail = userRepository.findByEmail(requestDto.getEmail());

        // 비밀번호 암호화 적용
        String rawPw = "";
        String encodePw = "";

        //이메일 중복 검사
        if (userEmail.isPresent()) {
            return SignUpPostResponse.builder()
                    .result("이미 등록된 이메일입니다. \n다른 이메일을 입력해주세요.")
                    .build();
        }else {
            //이메일 중복이 아닐 경우 회원 가입 진행
            rawPw = requestDto.getPassword();
            encodePw = passwordEncoder.encode(rawPw);

            User user = new User();
            user.setEmail(requestDto.getEmail());
            user.setPassword(encodePw);
            userRepository.save(user);

            return SignUpPostResponse.builder()
                    .result("회원 가입이 완료되었습니다.")
                    .build();

        }


    }


}
