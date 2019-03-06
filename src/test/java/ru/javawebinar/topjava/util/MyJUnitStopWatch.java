package ru.javawebinar.topjava.util;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MyJUnitStopWatch extends Stopwatch {

    private static final Logger log = Logger.getLogger(String.valueOf(MyJUnitStopWatch.class));

    private static StringBuilder sb = new StringBuilder().append("\nFINAL RESULTS\n");

    public static void finalResult() {
        log.info(String.valueOf(sb));
    }

    @Override
    protected void finished(long nanos, Description description) {
        String testName = description.getMethodName();
        String res = String.format("Test %s finished, spent %d milliseconds\n",
                testName, TimeUnit.NANOSECONDS.toMillis(nanos));
        sb.append(res);
        log.info(res);
    }
}
