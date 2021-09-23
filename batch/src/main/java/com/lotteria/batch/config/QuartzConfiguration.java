package com.lotteria.batch.config;

import com.lotteria.batch.quartz.QuartzTestJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class QuartzConfiguration {

    private final Logger logger = LoggerFactory.getLogger("QuartzConfiguration");

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void start() {
        try {
            JobDetail jobDetail1 = buildJobDetail(
                    QuartzTestJob.class,
                    "gradle-test-job-1",
                    "hello world",
                    new HashMap()
            );

            JobDetail jobDetail2 = buildJobDetail(
                    QuartzTestJob.class,
                    "gradle-test-job-2",
                    "hello world",
                    new HashMap()
            );

            scheduler.scheduleJob(
                    jobDetail1,
                    buildCronJobTrigger("0 * * * * ?")
            );
            scheduler.scheduleJob(
                    jobDetail2,
                    buildCronJobTrigger("0 * * * * ?")
            );

        } catch (SchedulerException schedulerException) {
            this.logger.error("error {}", schedulerException.toString());
        }
    }

    // *  *   *   *   *   *     *
    //초  분  시  일  월  요일  년도(생략가능)
    public Trigger buildCronJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
                .build();
    }

    public JobDetail buildJobDetail(Class job, String name, String description, Map params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);
        return JobBuilder
                .newJob(job)
                .withIdentity(name)
                .withDescription(description)
                .usingJobData(jobDataMap)
                .build();
    }
}
