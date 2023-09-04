package com.example.demo.controller;

import com.example.demo.dto.NumberSubmitDto;
import com.example.demo.dto.SignupRequestDto;
import com.example.demo.service.YaguService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/yagu")
@RequiredArgsConstructor
public class YaguController {

    private final YaguService yaguService;

    @PostMapping("/game")
    @ApiOperation(value = "회원가입")
    public String game(@Valid @RequestBody NumberSubmitDto numberSubmitDto) {
        return yaguService.game(numberSubmitDto);
    }
}
