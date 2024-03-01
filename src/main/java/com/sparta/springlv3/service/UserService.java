//package com.sparta.springlv3.service;
//
//
//import com.sparta.springlv3.dto.SignupRequestDto;
//import com.sparta.springlv3.entity.UserRoleEnum;
//import com.sparta.springlv3.entity.User;
//import com.sparta.springlv3.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    // ADMIN_TOKEN
//    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
//
//    public void signup(SignupRequestDto requestDto) {
//        String email = requestDto.getEmail();
//        String password = passwordEncoder.encode(requestDto.getPassword());
//        String userId = createUserId(); // 사용자 아이디 생성
//        String department = requestDto.getDepartment(); // 부서 코드 가져오기
//
//        // email 중복확인
//        Optional<User> checkEmail = userRepository.findByUsername(email);
//        if (checkEmail.isPresent()) {
//            throw new IllegalArgumentException("중복된 Email 입니다.");
//        }
//
//
//        // 사용자 ROLE 확인
//        UserRoleEnum authority = UserRoleEnum.USER;
//        if (requestDto.isAdmin()) {
//            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            authority = UserRoleEnum.ADMIN;
//        }
//
//        // 사용자 등록
//        User user = new User(userId, email, password, department, authority);
//        userRepository.save(user);
//    }
//
//    private String createUserId() {
//    }
//}