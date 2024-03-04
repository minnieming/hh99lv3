package com.sparta.springlv3.user.controller;

import com.sparta.springlv3.user.dto.*;
import com.sparta.springlv3.user.exception.NotFoundException;
import com.sparta.springlv3.user.exception.UnauthorizedException;
import com.sparta.springlv3.user.security.UserDetailsImpl;
import com.sparta.springlv3.user.service.UserService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.function.Supplier;


import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/user/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.signup(requestDto));
    }

    private ResponseEntity<?> handleRequest(Supplier<ResponseEntity<?>> supplier) {
        try {
            return supplier.get();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인터넷 서버 오류: " + e.getMessage());
        }
    }


    // 회원 관련 정보 받기
    @GetMapping("/user-info") // 사용자 정보 조회
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userEmail = userDetails.getUser().getEmail();
        String password = userDetails.getUser().getPassword();

        return new UserInfoDto(userEmail,password);

    }
}


