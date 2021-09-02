package com.lotteria.api.entity.pension;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Positive @Max(value = 5)
    @Column(name = "joNum", nullable = false)
    private int joNum;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "num1", nullable = false)
    private int num1;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "num2", nullable = false)
    private int num2;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "num3", nullable = false)
    private int num3;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "num4", nullable = false)
    private int num4;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "num5", nullable = false)
    private int num5;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "num6", nullable = false)
    private int num6;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "b_num1", nullable = false)
    private int bNum1;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "b_num2", nullable = false)
    private int bNum2;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "b_num3", nullable = false)
    private int bNum3;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "b_num4", nullable = false)
    private int bNum4;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "b_num5", nullable = false)
    private int bNum5;

    @Min(value = 0) @Max(value = 9)
    @Column(name = "b_num6", nullable = false)
    private int bNum6;

    @Column(name = "update_date")
    private Date updateDate;

    @Transient
    private List<Integer> numberList;

    @Transient
    private List<Integer> bonusNumberList;
}
