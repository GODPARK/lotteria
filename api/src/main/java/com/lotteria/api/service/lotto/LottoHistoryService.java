package com.lotteria.api.service.lotto;

import com.lotteria.api.entity.lotto.LottoHistory;
import com.lotteria.api.exception.NotFoundException;
import com.lotteria.api.repository.lotto.LottoHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LottoHistoryService {

    @Autowired
    private LottoHistoryRepository lottoHistoryRepository;

    public LottoHistory searchLottoByRoundNum(long roundNum) {
        Optional<LottoHistory> optionalLottoHistory = lottoHistoryRepository.findByRoundNum(roundNum);
        if (optionalLottoHistory.isEmpty()) throw new NotFoundException("lotto number is not found");
        return optionalLottoHistory.get();
    }
}
