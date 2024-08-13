package com.example.hecto.common.config;

import lombok.Getter;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Getter
public class RateLimiterConfig {
    private final String key;
    private final int time;
    private final TimeUnit timeUnit;
    private final int quota;

    private RateLimiterConfig(int time, TimeUnit timeUnit, int quota, String key) {
        this.time = time;
        this.timeUnit = timeUnit;
        this.quota = quota;
        this.key = key;
    }

    public static Builder custom() {
        return new Builder();
    }

    public static class Builder {
        private int time;
        private TimeUnit unit;
        private int quota;
        private String key;

        public Builder time(int time) {
            this.time = time;
            return this;
        }

        public Builder timeUnit(TimeUnit timeUnit) {
            this.unit = timeUnit;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder quota(int quota) {
            this.quota = quota;
            return this;
        }

        public RateLimiterConfig build() {
            return new RateLimiterConfig(time, unit, quota, key);
        }
    }
}

