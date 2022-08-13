package ua.edu.sumdu.nefodov.sheltered.service;

import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;

public interface CounterService {

    @Async
    @Transactional
    void receiveIncreaseSignal(double lat, double lng, int cnt);

    @Async
    @Transactional
    void receiveDecreaseSignal(double lat, double lng, int cnt);

    void enableCounter();

    void disableCounter();

    boolean isActive();
}
