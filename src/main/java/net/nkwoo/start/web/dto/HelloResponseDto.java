package net.nkwoo.start.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HelloResponseDto {
    private String name;
    private int amount;
}
