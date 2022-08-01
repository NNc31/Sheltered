package ua.edu.sumdu.nefodov.sheltered.service;

public interface CounterService {

    void receiveIncreaseSignal(double lat, double lng, int cnt);

    void receiveDecreaseSignal(double lat, double lng, int cnt);

    void enableCounter();

    void disableCounter();

    boolean isActive();
}
