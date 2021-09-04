package com.lotteria.api.controller.lotto;

import com.lotteria.api.entity.lotto.LottoHistory;
import com.lotteria.api.service.lotto.LottoHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/lotto_history")
public class LottoHistoryController {

    @Autowired
    private LottoHistoryService lottoHistoryService;

    @GetMapping(value = "/{roundNum}", consumes = "*/*", produces = "application/json")
    public ResponseEntity<LottoHistory> lottoHistoryByRoundNumApi (@PathVariable(value = "roundNum") long roundNum) {
        return ResponseEntity.ok().body(this.lottoHistoryService.searchLottoByRoundNum(roundNum));
    }
}
