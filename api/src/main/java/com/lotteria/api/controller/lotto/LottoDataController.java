package com.lotteria.api.controller.lotto;

import com.lotteria.api.entity.lotto.LottoHistory;
import com.lotteria.api.service.lotto.LottoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/lotto_data")
public class LottoDataController {

    @Autowired
    private LottoDataService lottoDataService;

    @PostMapping(value = "/sync/{roundNum}", consumes = "*/*", produces = "application/json")
    public ResponseEntity<LottoHistory> syncLottoDataApi(@PathVariable(value = "roundNum") long roundNum) {
        return ResponseEntity.ok().body(
                this.lottoDataService.syncDataRoundNum(roundNum)
        );
    }

    @PostMapping(value = "/sync/latest", consumes = "*/*", produces = "application/json")
    public ResponseEntity<LottoHistory> latestLottoDataApi() {
        return ResponseEntity.ok().body(
                this.lottoDataService.latestDataUpdate()
        );
    }
}
