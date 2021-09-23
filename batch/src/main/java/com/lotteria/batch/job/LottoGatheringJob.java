package com.lotteria.batch.job;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LottoGatheringJob extends QuartzJobBean implements InterruptableJob {

    private boolean isInterrupted = false;
    private JobKey jobKey = null;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        this.isInterrupted = true;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.jobKey = jobExecutionContext.getJobDetail().getKey();
        if (this.isInterrupted) {
            logger.warn("jobkey: {} is Interrupted", jobKey);
            return;
        }

    }
}
