package com.budgetwise.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MonthlyResetScheduler {
    private static final Logger logger = LoggerFactory.getLogger(MonthlyResetScheduler.class);

    // Ye har mahine ki 1 tareekh ko raat 12 baje chalega
    @Scheduled(cron = "0 0 0 1 * *")
    public void monthlyResetJob() {
        logger.info("Executing Monthly Reset Job: Archiving previous month's data...");
        // Future me yahan archiving logic daal sakte hain
    }
}