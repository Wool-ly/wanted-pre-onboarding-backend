package com.preonboarding.wanted.service;

import com.preonboarding.wanted.dto.request.LoginPostRequest;
import com.preonboarding.wanted.dto.request.SignUpPostRequest;
import com.preonboarding.wanted.dto.response.SignUpPostResponse;
import com.preonboarding.wanted.entity.User;
import com.preonboarding.wanted.entity.UserRole;
import com.preonboarding.wanted.exception.CustomException;
import com.preonboarding.wanted.exception.ErrorCode;
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
    private static final String ADMIN_PW = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

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

            //true면 == 관리자이면
            //boolean 타입의 getter는 is를 붙인다
            if (requestDto.isAdmin()) {
                if (!requestDto.getAdminToken().equals(ADMIN_PW)) {
                    throw new CustomException(ErrorCode.ADMIN_TOKEN);
                }
                //role을 admin으로 바꿔준다
                role = UserRole.ROLE_ADMIN;
            }

            User user = new User();
            user.setEmail(requestDto.getEmail());
            user.setPassword(encodePw);
            user.setRole(role);
            userRepository.save(user);

            return SignUpPostResponse.builder()
                    .result("회원 가입이 완료되었습니다.")
                    .build();

        }
    }

    public User userLogin(LoginPostRequest requestDto){
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_USER)
        );
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.NO_USER);
        }
        return user;
    }


}
