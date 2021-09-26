package com.lotteria.batch.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LotteriaApiException extends RuntimeException{
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    public LotteriaApiException(String message) {
        super(message);
        logger.info("lotteria api error {}", message);
    }
}
