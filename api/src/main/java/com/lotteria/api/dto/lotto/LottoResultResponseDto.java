package com.lotteria.api.dto.lotto;

import lombok.*;

import java.util.List;

@Getter @Setter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class LottoResultResponseDto {
    private long roundNum;
    private String result;
    private List<Integer> correctNumberList;
    private List<Integer> resultNumberList;
    private int resultBonus;
}
