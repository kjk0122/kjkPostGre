package com.example.demo.controller;

import com.example.demo.dto.NumberSubmitDto;
import com.example.demo.service.YaguService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/yagu")
@RequiredArgsConstructor
public class YaguController {

    private final YaguService yaguService;

    @PostMapping("/game")
    @ApiOperation(value = "야구게임")
    public String game(@Valid @RequestBody NumberSubmitDto numberSubmitDto, HttpServletRequest request) {
        return yaguService.game(numberSubmitDto, request);
    }
}
