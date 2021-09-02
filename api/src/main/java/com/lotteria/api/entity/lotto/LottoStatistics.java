package com.lotteria.api.entity.lotto;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lotto_statistics")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LottoStatistics {
    @Id
    @Column(name = "num")
    private int num;

    @Column(name = "count")
    private long count;

    @Transient
    private List<Integer> numberList;
}
