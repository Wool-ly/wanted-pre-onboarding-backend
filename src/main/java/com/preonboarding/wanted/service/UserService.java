package com.preonboarding.wanted.service;

import com.preonboarding.wanted.dto.request.LoginRequest;
import com.preonboarding.wanted.dto.request.SignUpRequest;
import com.preonboarding.wanted.dto.response.LoginResponse;
import com.preonboarding.wanted.dto.response.SignUpResponse;
import com.preonboarding.wanted.entity.User;
import com.preonboarding.wanted.entity.UserRole;
import com.preonboarding.wanted.exception.CustomException;
import com.preonboarding.wanted.exception.ErrorCode;
import com.preonboarding.wanted.repository.UserRepository;
import com.preonboarding.wanted.security.JwtTokenProvider;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
    public SignUpResponse userSignUp(SignUpRequest requestDto) {

        try {
            Optional<User> userEmail = userRepository.findByEmail(requestDto.getEmail());

            if (userEmail.isPresent()) {
                return SignUpResponse.builder()
                        .result("이미 등록된 이메일입니다. 다른 이메일을 입력해주세요.")
                        .build();
            } else {
                if (StringUtils.isBlank(requestDto.getPassword())
                        || requestDto.getPassword() == null
                        || requestDto.getPassword().trim().isEmpty()) {
                    return SignUpResponse.builder()
                            .result("비밀번호를 입력해주세요.")
                            .build();
                }

                // 비밀번호 암호화 적용
                String encodePw = passwordEncoder.encode(requestDto.getPassword());

                // 사용자 ROLE 확인
                UserRole role = UserRole.ROLE_MEMBER;

                User user = new User();
                user.setEmail(requestDto.getEmail());
                user.setPassword(encodePw);
                user.setRole(role);
                userRepository.save(user);

                return SignUpResponse
                        .builder()
                        .result("회원 가입이 완료되었습니다.")
                        .build();
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return SignUpResponse.builder()
                    .result("회원 가입 중 오류가 발생했습니다.")
                    .build();
        }
    }

    @Transactional
    public LoginResponse userLogin(LoginRequest requestDto){

        try{
            User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_USER)
            );
            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                throw new CustomException(ErrorCode.INVALID_PASSWORD);
            }

            UserRole role = user.getRole();

            return LoginResponse
                    .builder()
                    .email(user.getEmail())
                    .grantType("Bearer")
                    .accessToken(jwtTokenProvider.createToken(requestDto.getEmail(), role))
                    .result("로그인에 성공하였습니다.")
                    .build();
        } catch (CustomException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return LoginResponse
                    .builder()
                    .result("로그인에 실패하였습니다. 이메일과 비밀번호를 확인 후 재시도해주시기 바랍니다.")
                    .build();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return LoginResponse.builder()
                    .result("로그인 중 오류가 발생했습니다.")
                    .build();
        }
    }


}
