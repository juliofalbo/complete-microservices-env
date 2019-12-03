package com.julio.poc.microservices.frontend.config;

import static java.util.concurrent.TimeUnit.SECONDS;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignRetryer implements Retryer {

    private final int maxAttempts;
    private final long period;
    private final long maxPeriod;
    private int attempt;

    public FeignRetryer() {
      this(100, SECONDS.toMillis(1), 5);
    }

    public FeignRetryer(long period, long maxPeriod, int maxAttempts) {
      this.period = period;
      this.maxPeriod = maxPeriod;
      this.maxAttempts = maxAttempts;
      this.attempt = 1;
    }

    private long currentTimeMillis() {
      return System.currentTimeMillis();
    }

    public void continueOrPropagate(RetryableException e) {
        log.warn("Attempt number {} to call http method {} since we are received a response with status code {} and message {}",
                attempt, e.method(), e.status(), e.getLocalizedMessage());
      if (attempt++ >= maxAttempts) {
        throw e;
      }

      long interval;
      if (e.retryAfter() != null) {
        interval = e.retryAfter().getTime() - currentTimeMillis();
        if (interval > maxPeriod) {
          interval = maxPeriod;
        }
        if (interval < 0) {
          return;
        }
      } else {
        interval = nextMaxInterval();
      }
      try {
        Thread.sleep(interval);
      } catch (InterruptedException ignored) {
        Thread.currentThread().interrupt();
        throw e;
      }
    }

    /**
     * Calculates the time interval to a retry attempt. <br>
     * The interval increases exponentially with each attempt, at a rate of nextInterval *= 1.5
     * (where 1.5 is the backoff factor), to the maximum interval.
     *
     * @return time in nanoseconds from now until the next attempt.
     */
    private long nextMaxInterval() {
      long interval = (long) (period * Math.pow(1.5, attempt - 1));
      return Math.min(interval, maxPeriod);
    }

    @Override
    public Retryer clone() {
      return new FeignRetryer(period, maxPeriod, maxAttempts);
    }
  }