package com.lotteria.api.entity.pension;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

@Entity @Table(name = "pension_history")
@Builder @Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class PensionHistory {

    @Id
    @Column(name = "round_num")
    @Positive
    private long roundNum;

    @Positive @Max(value = 45)
    @Column(name = "num1", nullable = false)
    private int joNum;

    private int num1;

    private int num2;

    private int num3;

    private int num4;

    private int num5;

    private int num6;

    private int bNum1;

    private int bNum2;

    private int bNum3;

    private int bNum4;

    private int bNum5;

    private int bNum6;

    private Date updateDate;

    @Transient
    private List<Integer> numberList;

    @Transient
    private List<Integer> bonusNumberList;
}
