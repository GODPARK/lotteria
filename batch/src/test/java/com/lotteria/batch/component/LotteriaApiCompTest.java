package com.lotteria.batch.component;

import com.lotteria.batch.dto.lotteria.LotteriaLottoResponseDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LotteriaApiCompTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private LotteriaApiComponent lotteriaApiComponent;

    @Test
    public void testApi() {
        LotteriaLottoResponseDto lotteriaLottoResponseDto = lotteriaApiComponent.lotteriaLottoLatestNumberApi();
        logger.info(lotteriaLottoResponseDto.toString());
    }
}
