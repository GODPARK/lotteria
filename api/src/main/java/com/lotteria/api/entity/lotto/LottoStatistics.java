package com.lotteria.api.entity.lotto;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name = "lotto_statistics")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LottoStatistics {
    @Id @Positive @Max(value = 45)
    @Column(name = "num")
    private int num;

    @Column(name = "count")
    private long count;

    @Column(name = "update_date")
    private Date updateDate;
}
