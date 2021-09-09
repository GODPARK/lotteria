package com.lotteria.batch.quartz;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuartzTestJob extends QuartzJobBean implements InterruptableJob {

    private boolean isInterrupted = false;
    private JobKey jobKey = null;
    private final Logger logger = LoggerFactory.getLogger("QuartzTestJob");

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        this.isInterrupted = true;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        this.jobKey = jobExecutionContext.getJobDetail().getKey();
        if (this.isInterrupted) {
            logger.warn("jobKey: {} is Interrupted.", jobKey);
            return;
        }
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        logger.info("JOB EXECUTE");
    }
}
