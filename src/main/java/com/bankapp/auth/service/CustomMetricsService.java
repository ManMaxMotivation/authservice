package com.bankapp.auth.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CustomMetricsService {

    private final Counter registerCounter;
    private final Counter loginCounter;
    private final Counter logoutCounter;
    private final Counter isLoggedCounter;
    private final Counter getUserCounter;

    private final DistributionSummary registerSummary;
    private final DistributionSummary loginSummary;
    private final DistributionSummary logoutSummary;
    private final DistributionSummary isLoggedSummary;
    private final DistributionSummary getUserSummary;

    private final Timer registerTimer;
    private final Timer loginTimer;
    private final Timer logoutTimer;
    private final Timer isLoggedTimer;
    private final Timer getUserTimer;

    private final AtomicInteger registerCount = new AtomicInteger(0);
    private final AtomicInteger loginCount = new AtomicInteger(0);
    private final AtomicInteger logoutCount = new AtomicInteger(0);
    private final AtomicInteger isLoggedCount = new AtomicInteger(0);
    private final AtomicInteger getUserCount = new AtomicInteger(0);

    public CustomMetricsService(MeterRegistry meterRegistry) {
        // Counters
        this.registerCounter = Counter.builder("auth_register_total")
                .description("Total number of registration attempts")
                .tags("environment", "development")
                .register(meterRegistry);
        this.loginCounter = Counter.builder("auth_login_total")
                .description("Total number of login attempts")
                .tags("environment", "development")
                .register(meterRegistry);
        this.logoutCounter = Counter.builder("auth_logout_total")
                .description("Total number of logout attempts")
                .tags("environment", "development")
                .register(meterRegistry);
        this.isLoggedCounter = Counter.builder("auth_is_logged_total")
                .description("Total number of isLogged checks")
                .tags("environment", "development")
                .register(meterRegistry);
        this.getUserCounter = Counter.builder("auth_get_user_total")
                .description("Total number of getUser requests")
                .tags("environment", "development")
                .register(meterRegistry);

        // Distribution Summaries
        this.registerSummary = DistributionSummary.builder("auth_register_summary")
                .description("Summary of registration data (e.g., name length)")
                .tags("environment", "development")
                .register(meterRegistry);
        this.loginSummary = DistributionSummary.builder("auth_login_summary")
                .description("Summary of login data (e.g., password length)")
                .tags("environment", "development")
                .register(meterRegistry);
        this.logoutSummary = DistributionSummary.builder("auth_logout_summary")
                .description("Summary of logout data")
                .tags("environment", "development")
                .register(meterRegistry);
        this.isLoggedSummary = DistributionSummary.builder("auth_is_logged_summary")
                .description("Summary of isLogged checks")
                .tags("environment", "development")
                .register(meterRegistry);
        this.getUserSummary = DistributionSummary.builder("auth_get_user_summary")
                .description("Summary of getUser requests")
                .tags("environment", "development")
                .register(meterRegistry);

        // Timers
        this.registerTimer = Timer.builder("auth_register_duration")
                .description("Time taken for registration requests")
                .tags("environment", "development")
                .register(meterRegistry);
        this.loginTimer = Timer.builder("auth_login_duration")
                .description("Time taken for login requests")
                .tags("environment", "development")
                .register(meterRegistry);
        this.logoutTimer = Timer.builder("auth_logout_duration")
                .description("Time taken for logout requests")
                .tags("environment", "development")
                .register(meterRegistry);
        this.isLoggedTimer = Timer.builder("auth_is_logged_duration")
                .description("Time taken for isLogged checks")
                .tags("environment", "development")
                .register(meterRegistry);
        this.getUserTimer = Timer.builder("auth_get_user_duration")
                .description("Time taken for getUser requests")
                .tags("environment", "development")
                .register(meterRegistry);
    }

    // Counter methods
    public void incrementRegisterCounter() {
        registerCounter.increment();
        registerCount.incrementAndGet();
    }

    public void incrementLoginCounter() {
        loginCounter.increment();
        loginCount.incrementAndGet();
    }

    public void incrementLogoutCounter() {
        logoutCounter.increment();
        logoutCount.incrementAndGet();
    }

    public void incrementIsLoggedCounter() {
        isLoggedCounter.increment();
        isLoggedCount.incrementAndGet();
    }

    public void incrementGetUserCounter() {
        getUserCounter.increment();
        getUserCount.incrementAndGet();
    }

    // DistributionSummary methods
    public void recordRegisterSummary(double value) {
        registerSummary.record(value);
    }

    public void recordLoginSummary(double value) {
        loginSummary.record(value);
    }

    public void recordLogoutSummary(double value) {
        logoutSummary.record(value);
    }

    public void recordIsLoggedSummary(double value) {
        isLoggedSummary.record(value);
    }

    public void recordGetUserSummary(double value) {
        getUserSummary.record(value);
    }

    // Timer methods
    public void recordRegisterTimer(long durationNanos) {
        registerTimer.record(durationNanos, TimeUnit.NANOSECONDS);
    }

    public void recordLoginTimer(long durationNanos) {
        loginTimer.record(durationNanos, TimeUnit.NANOSECONDS);
    }

    public void recordLogoutTimer(long durationNanos) {
        logoutTimer.record(durationNanos, TimeUnit.NANOSECONDS);
    }

    public void recordIsLoggedTimer(long durationNanos) {
        isLoggedTimer.record(durationNanos, TimeUnit.NANOSECONDS);
    }

    public void recordGetUserTimer(long durationNanos) {
        getUserTimer.record(durationNanos, TimeUnit.NANOSECONDS);
    }

    // Gauge support
    public int getRegisterCount() {
        return registerCount.get();
    }

    public int getLoginCount() {
        return loginCount.get();
    }

    public int getLogoutCount() {
        return loginCount.get();
    }

    public int getIsLoggedCount() {
        return isLoggedCount.get();
    }

    public int getGetUserCount() {
        return getUserCount.get();
    }
}