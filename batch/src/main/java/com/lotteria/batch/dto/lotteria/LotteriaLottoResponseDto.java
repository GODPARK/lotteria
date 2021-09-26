package com.lotteria.batch.dto.lotteria;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class LotteriaLottoResponseDto {

    private long roundNum;

    private int num1;
    private int num2;
    private int num3;
    private int num4;
    private int num5;
    private int num6;

    private int bonus;
    private List<Integer> numberList;
    private Date createDate;
    private Date updateDate;

}
