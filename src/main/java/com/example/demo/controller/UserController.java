package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.SignupRequestDto;
import com.example.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("로그인 완료되었습니다.");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "회원탈퇴")
    public String signOut(@PathVariable Long id) {
        userService.deleteUser(id);
        return "감사합니다.";
    }
}