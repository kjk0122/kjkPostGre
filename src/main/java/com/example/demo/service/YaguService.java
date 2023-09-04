package com.example.demo.service;


import com.example.demo.dto.NumberSubmitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class YaguService {
    public String game(@RequestBody NumberSubmitDto numberSubmitDto) {
        String input= numberSubmitDto.getNumber();
        String answer="3216";
        int S=0;
        int B=0;
        for(int i=0;i<4;i++){
            String a= String.valueOf(input.charAt(i));
            if(answer.charAt(i)==input.charAt(i)){
            S++;
            } else if (answer.contains(a)) {
            B++;
            }
        }

        if (S==4) {
            return "정답!";
        } else if (S!=0 || B!=0) {
            return "스트라이크 갯수: " + S +" 볼 갯수: " + B;
        } else {
            return "아웃!";
        }
    }
}
