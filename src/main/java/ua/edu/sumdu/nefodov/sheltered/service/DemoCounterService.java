package ua.edu.sumdu.nefodov.sheltered.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.nefodov.sheltered.model.Shelter;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class DemoCounterService implements CounterService {

    private Timer timer;

    private ShelterService shelterService;

    private final int CNT_BOUND = 5;
    private final int DELAY_BOUND = 60 * 1000;

    @Autowired
    public DemoCounterService(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @Override
    public void receiveSignal(double lat, double lng, int cnt) {
        shelterService.updateCounter(lat, lng, cnt);
    }

    @Override
    public void receiveSingleSignal(double lat, double lng) {
        shelterService.incrementCounter(lat, lng);
    }

    @Override
    public boolean pingCounter() {
        return timer != null;
    }

    @Override
    public void enableCounter() {
        timer = new Timer();
        setInitSignal();
    }

    @Override
    public void disableCounter() {
        timer.cancel();
        timer.purge();
    }

    private void setInitSignal() {
        timer.schedule(new SendSignalTask(), DELAY_BOUND);
    }

    public class SendSignalTask extends TimerTask {

        @Override
        public void run() {
            Random rnd = new Random();

            List<Shelter> shelters = shelterService.findAll();
            Shelter rndShelter = shelters.get(rnd.nextInt(shelters.size()));
            if (rndShelter.getCounter() < rndShelter.getCapacity()) {
                receiveSignal(rndShelter.getLatitude(), rndShelter.getLongitude(), rnd.nextInt(CNT_BOUND));
                timer.schedule(new SendSignalTask(), rnd.nextInt(DELAY_BOUND));
            } else {
                receiveSingleSignal(rndShelter.getLatitude(), rndShelter.getLongitude());
                timer.schedule(new SendSignalTask(), rnd.nextInt(DELAY_BOUND, 2 * DELAY_BOUND));
            }
        }
    }
}