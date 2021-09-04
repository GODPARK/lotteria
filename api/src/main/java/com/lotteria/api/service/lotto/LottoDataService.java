package com.lotteria.api.service.lotto;

import com.lotteria.api.component.lotto.LottoCrawlingComponent;
import com.lotteria.api.dto.lotto.LottoHistoryRequestDto;
import com.lotteria.api.entity.lotto.LottoHistory;
import com.lotteria.api.entity.lotto.LottoStatistics;
import com.lotteria.api.exception.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LottoDataService {

    @Autowired
    private LottoCrawlingComponent lottoCrawlingComponent;

    @Autowired
    private LottoHistoryService lottoHistoryService;

    @Autowired
    private LottoStatisticsService lottoStatisticsService;

    public LottoHistory syncDataRoundNum(long roundNum) {
        return this.lottoHistoryService.editLottoHistory(
                this.lottoCrawlingComponent.getNumberBydHLottoInHtml(
                        this.lottoHistoryService.searchLottoByRoundNum(roundNum).getRoundNum()
                )
        );
    }

    public LottoHistory latestLottoDataUpdate() {
        LottoHistory latestInDb = this.lottoHistoryService.searchLottoLatest();
        LottoHistoryRequestDto latestInWeb = this.lottoCrawlingComponent.getNumberBydHLottoInHtmLatest();
        if (latestInDb.getRoundNum() != latestInWeb.getRoundNum()) {
            return this.lottoHistoryService.saveLottoHistory(latestInWeb);
        } else {
            return this.lottoHistoryService.editLottoHistory(latestInWeb);
        }
    }

    public List<LottoStatistics> latestStatisticsDataUpdate() {
        List<LottoStatistics> latestInWeb = this.lottoCrawlingComponent.getStatistics();
        if (latestInWeb.size() != 45) throw new ServerErrorException("web statistics size is not 45");
        List<LottoStatistics> resultList = new ArrayList<>();
        for (LottoStatistics saveLottoStatistics : latestInWeb) {
            resultList.add(
                    this.lottoStatisticsService.editLottoStatistics(saveLottoStatistics)
            );
        }
        return resultList;
    }

}
