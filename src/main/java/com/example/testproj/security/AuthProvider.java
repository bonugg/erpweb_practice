package com.example.testproj.security;

import com.example.testproj.User.SessionUser;
import com.example.testproj.User.User;
import com.example.testproj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private HttpSession httpSession;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String CNO_S = (String) authentication.getPrincipal(); // 로그인 창에 입력한 id
            String PWD = (String) authentication.getCredentials(); // 로그인 창에 입력한 password
            long CNO;
            try {
                CNO = Long.parseLong(CNO_S);
                // 숫자 타입일 때 처리할 로직
            } catch (NumberFormatException e) {
                CNO = 0;
            }
            PasswordEncoder passwordEncoder = passwordEncoder();
            UsernamePasswordAuthenticationToken token;
            User user = userRepository.findByCNO(CNO).get();

            if (user != null && passwordEncoder.matches(PWD, user.getPWD())) { // 일치하는 user 정보가 있는지 확인
                List<GrantedAuthority> roles = new ArrayList<>();
                roles.add(new SimpleGrantedAuthority("ROLE_USER")); // 권한 부여

                token = new UsernamePasswordAuthenticationToken(user.getCNO(), null, roles);
                httpSession.setAttribute("user", new SessionUser(user));

                return token;
            }
        }catch (NoSuchElementException ne){
        }
        throw new BadCredentialsException("아이디 또는 비밀번호를 찾을 수 없습니다");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }
}
