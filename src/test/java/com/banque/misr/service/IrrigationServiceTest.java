package com.banque.misr.service;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.internal.CircuitBreakerStateMachine;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {IrrigationService.class})
@ExtendWith(SpringExtension.class)
class IrrigationServiceTest {
    @MockBean
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private IrrigationService irrigationService;

    @MockBean
    private PlotService plotService;

    @Test
    void testIrrigateLand() {
        CircuitBreakerConfig circuitBreakerConfig = mock(CircuitBreakerConfig.class);
//        when(circuitBreakerConfig.getFailureRateThreshold()).thenReturn(10.0f);
//        when(circuitBreakerConfig.getSlowCallRateThreshold()).thenReturn(10.0f);
//        when(circuitBreakerConfig.getMinimumNumberOfCalls()).thenReturn(1);
//        when(circuitBreakerConfig.getSlidingWindowSize()).thenReturn(1);
//        when(circuitBreakerConfig.getSlidingWindowType()).thenReturn(CircuitBreakerConfig.SlidingWindowType.TIME_BASED);
//        when(circuitBreakerConfig.getSlowCallDurationThreshold()).thenReturn(null);
//        when(circuitBreakerConfig.getTimestampUnit()).thenReturn(TimeUnit.NANOSECONDS);
//        when(circuitBreakerConfig.getCurrentTimestampFunction()).thenReturn(mock(Function.class));
        TimeLimiterConfig timeLimiterConfig = mock(TimeLimiterConfig.class);
        when(timeLimiterConfig.shouldCancelRunningFuture()).thenReturn(true);
        when(timeLimiterConfig.getTimeoutDuration()).thenReturn(null);
        CircuitBreakerRegistry circuitBreakerRegistry = mock(CircuitBreakerRegistry.class);
        when(circuitBreakerRegistry.circuitBreaker(Mockito.<String>any(), Mockito.<CircuitBreakerConfig>any(),
                Mockito.<Map<String, String>>any())).thenReturn(new CircuitBreakerStateMachine("Name"));
        ForkJoinPool executorService = ForkJoinPool.commonPool();
        Resilience4JCircuitBreaker resilience4JCircuitBreaker = new Resilience4JCircuitBreaker("42", circuitBreakerConfig,
                timeLimiterConfig, circuitBreakerRegistry, executorService, Optional.empty());

        when(circuitBreakerFactory.create(Mockito.<String>any())).thenReturn(resilience4JCircuitBreaker);
        assertEquals(1, irrigationService.irrigateLand(1L));
        verify(circuitBreakerFactory).create(Mockito.<String>any());
        verify(timeLimiterConfig).getTimeoutDuration();
        verify(circuitBreakerRegistry).circuitBreaker(Mockito.<String>any(), Mockito.<CircuitBreakerConfig>any(),
                Mockito.<Map<String, String>>any());
    }


    @Test
    void testIrrigateLand2() {
        CircuitBreakerConfig circuitBreakerConfig = mock(CircuitBreakerConfig.class);
//        when(circuitBreakerConfig.getFailureRateThreshold()).thenReturn(10.0f);
//        when(circuitBreakerConfig.getSlowCallRateThreshold()).thenReturn(10.0f);
//        when(circuitBreakerConfig.getMinimumNumberOfCalls()).thenReturn(1);
//        when(circuitBreakerConfig.getSlidingWindowSize()).thenReturn(1);
//        when(circuitBreakerConfig.getSlidingWindowType()).thenReturn(CircuitBreakerConfig.SlidingWindowType.TIME_BASED);
//        when(circuitBreakerConfig.getSlowCallDurationThreshold()).thenReturn(null);
//        when(circuitBreakerConfig.getTimestampUnit()).thenReturn(TimeUnit.NANOSECONDS);
//        when(circuitBreakerConfig.getCurrentTimestampFunction()).thenReturn(mock(Function.class));
        TimeLimiterConfig timeLimiterConfig = mock(TimeLimiterConfig.class);
        when(timeLimiterConfig.shouldCancelRunningFuture()).thenReturn(true);
        when(timeLimiterConfig.getTimeoutDuration()).thenReturn(null);
        CircuitBreakerRegistry circuitBreakerRegistry = mock(CircuitBreakerRegistry.class);
        when(circuitBreakerRegistry.circuitBreaker(Mockito.<String>any(), Mockito.<CircuitBreakerConfig>any(),
                Mockito.<Map<String, String>>any())).thenReturn(new CircuitBreakerStateMachine("Name"));
        Resilience4JCircuitBreaker resilience4JCircuitBreaker = new Resilience4JCircuitBreaker("42", circuitBreakerConfig,
                timeLimiterConfig, circuitBreakerRegistry, null, Optional.empty());

        when(circuitBreakerFactory.create(Mockito.<String>any())).thenReturn(resilience4JCircuitBreaker);
        assertEquals(1, irrigationService.irrigateLand(1L));
        verify(circuitBreakerFactory).create(Mockito.<String>any());
        verify(circuitBreakerRegistry).circuitBreaker(Mockito.<String>any(), Mockito.<CircuitBreakerConfig>any(),
                Mockito.<Map<String, String>>any());
    }
}

