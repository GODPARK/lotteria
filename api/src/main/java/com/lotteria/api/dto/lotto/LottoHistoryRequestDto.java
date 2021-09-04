package com.lotteria.api.dto.lotto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lotteria.api.entity.lotto.LottoHistory;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import java.util.Comparator;
import java.util.List;

@ToString @Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class LottoHistoryRequestDto {

    @Positive
    private long roundNum;

    private List<Integer> numberList;

    @Positive @Max(value = 45)
    private int bonus;

    @JsonIgnore
    public boolean validationCheck() {
        this.numberList.sort(Comparator.naturalOrder());
        if (this.numberList.size() != 6) return false;
        if (this.numberList.contains(this.bonus)) return false;
        if (this.numberList.stream().distinct().count() != 6) return false;
        if (this.numberList.get(5) > 45 || this.numberList.get(0) < 1) return false;
        return true;
    }

    @JsonIgnore
    public LottoHistory mapEntity() {
        this.numberList.sort(Comparator.naturalOrder());
        return LottoHistory.builder()
                .roundNum(roundNum)
                .num1(this.numberList.get(0)).num2(this.numberList.get(1))
                .num3(this.numberList.get(2)).num4(this.numberList.get(3))
                .num5(this.numberList.get(4)).num6(this.numberList.get(5))
                .bonus(this.bonus).build();
    }
}
