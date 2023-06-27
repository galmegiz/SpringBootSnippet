package com.example.demo.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Member {
    @Size(min = 10, message = "내용은 최대 1000자를 넘을 수 없습니다.")
    @Setter
    String username;

    @Min(10)
    int age;
}
