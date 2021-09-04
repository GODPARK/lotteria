package com.lotteria.api.service.lotto;

import com.lotteria.api.entity.lotto.LottoStatistics;
import com.lotteria.api.exception.NotFoundException;
import com.lotteria.api.repository.lotto.LottoStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LottoStatisticsService {

    @Autowired
    private LottoStatisticsRepository lottoStatisticsRepository;

    public LottoStatistics searchLottoStatistics(int num) {
        Optional<LottoStatistics> lottoStatistics = this.lottoStatisticsRepository.findByNum(num);
        if (lottoStatistics.isEmpty()) throw new NotFoundException("lotto statistics number is empty");
        return lottoStatistics.get();
    }

    public List<LottoStatistics> searchLottoStatisticsAll() {
        List<LottoStatistics> lottoStatisticsList = this.lottoStatisticsRepository.findAll();
        if (lottoStatisticsList.size() != 45) throw new NotFoundException("lotto statistics number list is not 45");
        return lottoStatisticsList;
    }

    public LottoStatistics editLottoStatistics (LottoStatistics lottoStatistics) {
        LottoStatistics editLottoStatistics = this.searchLottoStatistics(lottoStatistics.getNum());
        editLottoStatistics.setCount(lottoStatistics.getCount());
        editLottoStatistics.setUpdateDate(new Date());
        return this.lottoStatisticsRepository.save(editLottoStatistics);
    }
}
