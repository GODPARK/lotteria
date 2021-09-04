package com.lotteria.api.service.lotto;

import com.lotteria.api.dto.lotto.LottoHistoryRequestDto;
import com.lotteria.api.dto.lotto.LottoResultRequestDto;
import com.lotteria.api.dto.lotto.LottoResultResponseDto;
import com.lotteria.api.entity.lotto.LottoHistory;
import com.lotteria.api.exception.BadRequestException;
import com.lotteria.api.exception.ConflictException;
import com.lotteria.api.exception.NotFoundException;
import com.lotteria.api.repository.lotto.LottoHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LottoHistoryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private LottoHistoryRepository lottoHistoryRepository;

    private LottoHistory insertUpdateLottoHistory(@Valid LottoHistory lottoHistory) {
        LottoHistory result = this.lottoHistoryRepository.save(lottoHistory);
        result.setNumberList();
        return result;
    }

    public LottoHistory searchLottoByRoundNum(long roundNum) {
        Optional<LottoHistory> optionalLottoHistory = lottoHistoryRepository.findByRoundNum(roundNum);
        if (optionalLottoHistory.isEmpty()) throw new NotFoundException("lotto number is not found");
        optionalLottoHistory.get().setNumberList();
        return optionalLottoHistory.get();
    }

    public LottoHistory searchLottoLatest() {
        Optional<LottoHistory> optionalLottoHistory = lottoHistoryRepository.findFirstByOrderByRoundNumDesc();
        if (optionalLottoHistory.isEmpty()) throw new NotFoundException("lotto number is not found");
        optionalLottoHistory.get().setNumberList();
        return optionalLottoHistory.get();
    }

    public LottoHistory saveLottoHistory(LottoHistoryRequestDto lottoHistoryRequestDto) {
        if(!lottoHistoryRequestDto.validationCheck()) throw new BadRequestException("request validation check");
        LottoHistory lottoHistory = lottoHistoryRequestDto.mapEntity();

        if (this.lottoHistoryRepository.existsByRoundNum(lottoHistory.getRoundNum())) throw new ConflictException("already exist");
        logger.info(lottoHistoryRequestDto.toString());
        lottoHistory.setCreateDate(new Date());
        return this.insertUpdateLottoHistory(lottoHistory);
    }

    public LottoHistory editLottoHistory(LottoHistoryRequestDto lottoHistoryRequestDto) {
        if(!lottoHistoryRequestDto.validationCheck()) throw new BadRequestException("request validation check");
        LottoHistory lottoHistory = lottoHistoryRequestDto.mapEntity();

        LottoHistory editLottoHistory = this.searchLottoByRoundNum(lottoHistory.getRoundNum());
        lottoHistory.setRoundNum(editLottoHistory.getRoundNum());
        lottoHistory.setUpdateDate(new Date());
        return this.insertUpdateLottoHistory(lottoHistory);
    }

    public LottoResultResponseDto resultLottoHistory(LottoResultRequestDto lottoResultRequestDto) {
        if(!lottoResultRequestDto.validationCheck()) throw new BadRequestException("request validation check");
        LottoHistory lottoHistory = this.searchLottoByRoundNum(lottoResultRequestDto.getRoundNum());
        lottoHistory.setNumberList();
        List<Integer> compareList = new ArrayList<>(lottoHistory.getNumberList());
        compareList.retainAll(lottoResultRequestDto.getNumberList());

        LottoResultResponseDto lottoResultResponseDto = LottoResultResponseDto.builder()
                .roundNum(lottoHistory.getRoundNum())
                .correctNumberList(compareList)
                .resultNumberList(lottoHistory.getNumberList())
                .resultBonus(lottoHistory.getBonus())
                .build();
        if (compareList.size() == 6 ) {
            lottoResultResponseDto.setResult("1등");
        } else if (compareList.size() == 5 && lottoResultRequestDto.getNumberList().contains(lottoHistory.getBonus())) {
            lottoResultResponseDto.setResult("2등");
        } else if (compareList.size() == 5 && !lottoResultRequestDto.getNumberList().contains(lottoHistory.getBonus()) ) {
            lottoResultResponseDto.setResult("3등");
        } else if (compareList.size() == 4) {
            lottoResultResponseDto.setResult("4등");
        } else if (compareList.size() == 3) {
            lottoResultResponseDto.setResult("5등");
        } else {
            lottoResultResponseDto.setResult("낙첨");
        }
        return lottoResultResponseDto;
    }
}
