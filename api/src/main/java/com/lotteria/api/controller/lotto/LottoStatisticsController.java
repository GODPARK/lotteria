package com.lotteria.api.controller.lotto;

import com.lotteria.api.entity.lotto.LottoStatistics;
import com.lotteria.api.service.lotto.LottoStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/lotto_statistics")
public class LottoStatisticsController {

    @Autowired
    private LottoStatisticsService lottoStatisticsService;

    @GetMapping(value = "/{num}", consumes = "*/*", produces = "application/json")
    public ResponseEntity<LottoStatistics> lottoStatisticsApi(@PathVariable(value = "num") int num) {
        return ResponseEntity.ok().body(this.lottoStatisticsService.searchLottoStatistics(num));
    }

    @GetMapping(value = "/all", consumes = "*/*", produces = "application/json")
    public ResponseEntity<List<LottoStatistics>> lottoStatisticsAllApi() {
        return ResponseEntity.ok().body(
                this.lottoStatisticsService.searchLottoStatisticsAll()
        );
    }

    @PatchMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LottoStatistics> lottoStatisticsEditApi(@RequestBody LottoStatistics lottoStatistics) {
        return ResponseEntity.ok().body(
                this.lottoStatisticsService.editLottoStatistics(lottoStatistics)
        );
    }
}
