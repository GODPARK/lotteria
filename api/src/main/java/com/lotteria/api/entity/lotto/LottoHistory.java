package com.lotteria.api.entity.lotto;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lotto_history")
@Builder @Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class LottoHistory {

    @Positive
    @Id
    @Column(name = "round_num")
    private long roundNum;

    @Positive @Max(value = 45)
    @Column(name = "num1", nullable = false)
    private int num1;

    @Positive @Max(value = 45)
    @Column(name = "num2", nullable = false)
    private int num2;

    @Positive @Max(value = 45)
    @Column(name = "num3", nullable = false)
    private int num3;

    @Positive @Max(value = 45)
    @Column(name = "num4", nullable = false)
    private int num4;

    @Positive @Max(value = 45)
    @Column(name = "num5", nullable = false)
    private int num5;

    @Positive @Max(value = 45)
    @Column(name = "num6", nullable = false)
    private int num6;

    @Positive @Max(value = 45)
    @Column(name = "bonus", nullable = false)
    private int bonus;

    @Column(name = "update_date")
    private Date updateDate;

    @Transient
    private List<Integer> numberList() {
        return new ArrayList<>(
                Arrays.asList(this.num1, this.num2, this.num3, this.num4, this.num5, this.num6)
        );
    }
}
