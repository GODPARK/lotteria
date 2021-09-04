package com.lotteria.api;

import com.lotteria.api.component.lotto.LottoCrawlingComponent;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LottoPageCrawlingTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private LottoCrawlingComponent lottoCrawlingComponent;

    @Test
    public void checkLottoInHtml() {
        logger.info(this.lottoCrawlingComponent.getNumberBydHLottoInHtml(979).toString());
    }

    @Test
    public void checkLottoLatest() {
        logger.info(this.lottoCrawlingComponent.getNumberBydHLottoInHtmLatest().toString());
    }

}
