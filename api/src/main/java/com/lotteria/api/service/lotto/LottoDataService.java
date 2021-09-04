package com.lotteria.api.service.lotto;

import com.lotteria.api.component.lotto.LottoCrawlingComponent;
import com.lotteria.api.dto.lotto.LottoHistoryRequestDto;
import com.lotteria.api.entity.lotto.LottoHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LottoDataService {

    @Autowired
    private LottoCrawlingComponent lottoCrawlingComponent;

    @Autowired
    private LottoHistoryService lottoHistoryService;

    public LottoHistory syncDataRoundNum(long roundNum) {
        return this.lottoHistoryService.editLottoHistory(
                this.lottoCrawlingComponent.getNumberBydHLottoInHtml(
                        this.lottoHistoryService.searchLottoByRoundNum(roundNum).getRoundNum()
                )
        );
    }

    public LottoHistory latestDataUpdate() {
        LottoHistory latestInDb = this.lottoHistoryService.searchLottoLatest();
        LottoHistoryRequestDto latestInWeb = this.lottoCrawlingComponent.getNumberBydHLottoInHtmLatest();
        if (latestInDb.getRoundNum() != latestInWeb.getRoundNum()) {
            return this.lottoHistoryService.saveLottoHistory(latestInWeb);
        } else {
            return this.lottoHistoryService.editLottoHistory(latestInWeb);
        }
    }

}
