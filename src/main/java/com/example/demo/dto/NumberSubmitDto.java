package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
public class NumberSubmitDto { //이건 일단 만들어두고 안쓰면 지우기
    private String number;
}
