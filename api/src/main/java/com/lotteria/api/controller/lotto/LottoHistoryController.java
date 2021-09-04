package com.lotteria.api.controller.lotto;

import com.lotteria.api.dto.lotto.LottoHistoryRequestDto;
import com.lotteria.api.dto.lotto.LottoResultResponseDto;
import com.lotteria.api.entity.lotto.LottoHistory;
import com.lotteria.api.service.lotto.LottoHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/lotto_history")
public class LottoHistoryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private LottoHistoryService lottoHistoryService;

    @GetMapping(value = "/{roundNum}", consumes = "*/*", produces = "application/json")
    public ResponseEntity<LottoHistory> lottoHistoryByRoundNumApi (@PathVariable(value = "roundNum") long roundNum) {
        return ResponseEntity.ok().body(this.lottoHistoryService.searchLottoByRoundNum(roundNum));
    }

    @GetMapping(value = "/latest", consumes = "*/*", produces = "application/json")
    public ResponseEntity<LottoHistory> lottoHistoryLatestApi () {
        return ResponseEntity.ok().body(this.lottoHistoryService.searchLottoLatest());
    }

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LottoHistory> saveLottoHistory (@Valid @RequestBody LottoHistoryRequestDto lottoHistoryRequestDto) {
        return ResponseEntity.ok().body(this.lottoHistoryService.saveLottoHistory(lottoHistoryRequestDto));
    }

    @PatchMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LottoHistory> editLottoHistory (@Valid @RequestBody LottoHistoryRequestDto lottoHistoryRequestDto) {
        return ResponseEntity.ok().body(this.lottoHistoryService.editLottoHistory(lottoHistoryRequestDto));
    }

    @GetMapping(value = "/result", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LottoResultResponseDto> resultLottoHistory(@Valid @RequestBody LottoHistoryRequestDto lottoHistoryRequestDto) {
        return ResponseEntity.ok().body(this.lottoHistoryService.resultLottoHistory(lottoHistoryRequestDto));
    }
}
