package ua.edu.sumdu.nefodov.sheltered.service;

public interface CounterService {


    void receiveSignal(double lat, double lng, int cnt);

    void receiveSingleSignal(double lat, double lng);

    boolean pingCounter();

    void enableCounter();

    void disableCounter();
}
