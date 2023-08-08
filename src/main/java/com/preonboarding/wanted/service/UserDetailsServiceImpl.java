package com.preonboarding.wanted.service;

import com.preonboarding.wanted.entity.User;
import com.preonboarding.wanted.exception.CustomJwtRuntimeException;
import com.preonboarding.wanted.repository.UserRepository;
import com.preonboarding.wanted.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {

    private final UserRepository userRepository;

    //로그인할 때 들어온 username으로 DB에서 정보 찾기
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(CustomJwtRuntimeException::new);

        //UserDetailsImpl에서 정의한 생성자
        return new UserDetailsImpl(user);
    }


}
