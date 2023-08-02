package com.preonboarding.wanted.service;

import com.preonboarding.wanted.dto.request.LoginPostRequest;
import com.preonboarding.wanted.dto.request.SignUpPostRequest;
import com.preonboarding.wanted.dto.response.LoginPostResponse;
import com.preonboarding.wanted.dto.response.SignUpPostResponse;
import com.preonboarding.wanted.entity.User;
import com.preonboarding.wanted.entity.UserRole;
import com.preonboarding.wanted.exception.CustomException;
import com.preonboarding.wanted.exception.ErrorCode;
import com.preonboarding.wanted.repository.UserRepository;
import com.preonboarding.wanted.security.JwtTokenProvider;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_PW = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public SignUpPostResponse userSignUp(SignUpPostRequest requestDto) {

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

            // 사용자 ROLE 확인
            UserRole role = UserRole.ROLE_MEMBER;

            User user = new User();
            user.setEmail(requestDto.getEmail());
            user.setPassword(encodePw);
            user.setRole(role);
            userRepository.save(user);

            return SignUpPostResponse
                    .builder()
                    .email(user.getEmail())
                    .result("회원 가입이 완료되었습니다.")
                    .build();

        }
    }

    @Transactional
    public LoginPostResponse userLogin(LoginPostRequest requestDto){

        try{

            User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                    () -> new CustomException(ErrorCode.INPUT_VALUE_INVALID)
            );
            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                throw new CustomException(ErrorCode.NO_USER);
            }

            UserRole role = user.getRole();

            return LoginPostResponse
                    .builder()
                    .email(user.getEmail())
                    .grantType("Bearer")
                    .accessToken(jwtTokenProvider.createToken(requestDto.getEmail(), role))
                    .result("로그인에 성공하였습니다.  ")
                    .build();
        } catch (CustomException ex){
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
    }


}
