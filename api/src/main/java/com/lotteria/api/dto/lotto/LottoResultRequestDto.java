package com.lotteria.api.dto.lotto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Positive;
import java.util.Comparator;
import java.util.List;

@Getter @Setter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class LottoResultRequestDto {

    @Positive
    private long roundNum;

    private List<Integer> numberList;

    @JsonIgnore
    public boolean validationCheck() {
        this.numberList.sort(Comparator.naturalOrder());
        if (this.numberList.size() != 6) return false;
        if (this.numberList.stream().distinct().count() != 6) return false;
        if (this.numberList.get(5) > 45 || this.numberList.get(0) < 1) return false;
        return true;
    }
}
