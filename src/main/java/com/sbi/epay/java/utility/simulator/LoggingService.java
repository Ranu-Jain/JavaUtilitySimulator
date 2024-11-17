package com.sbi.epay.java.utility.simulator;

import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;

public class LoggingService {
    public static void main(String[] args) {
        LoggerUtility logger = LoggerFactoryUtility.getLogger(LoggingService.class);
        logger.trace("In Trace");
        logger.debug("In Debug");
        logger.error("In Error");
        logger.info("In Info");
        logger.warn("In Warning");
        System.out.println("Hello world!");

    }
}