package ua.edu.sumdu.nefodov.sheltered.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.nefodov.sheltered.application.model.Coordinates;
import ua.edu.sumdu.nefodov.sheltered.application.model.Shelter;
import ua.edu.sumdu.nefodov.sheltered.application.repository.ShelterRepository;

import java.util.List;

@Service
public class ShelterFinderService {

    private final ShelterRepository shelterRepo;

    @Autowired
    public ShelterFinderService(ShelterRepository shelterRepo) {
        this.shelterRepo = shelterRepo;
    }

    public Coordinates findClosestCoordinates(Coordinates userCoords) {
        List<Coordinates> coordinates = shelterRepo.findAllCoordinates();
        double minLat = 0;
        double minLng = 0;
        double minDistance = -1;
        double posLat = userCoords.getLatitude();
        double posLng = userCoords.getLongitude();
        if (coordinates.size() > 0) {
            for (Coordinates coords : coordinates) {
                double x = coords.getLatitude() - posLat;
                double y = coords.getLongitude() - posLng;
                double distance = Math.sqrt(x * x + y * y);

                if (minDistance == -1 || distance < minDistance) {
                    minDistance = distance;
                    minLat = coords.getLatitude();
                    minLng = coords.getLongitude();
                }
            }
            return new Coordinates(minLat, minLng);
        } else {
            return new Coordinates(91, 181);
        }

        /*
    let minDistance = null;
    if (shelters.length > 0) {
        for (let i = 0; i < coords.length; i++) {
            let x = coords[i][0] - posLat;
            let y = coords[i][1] - posLng;
            let distance = Math.sqrt(x * x + y * y);

            if (minDistance === null || distance < minDistance) {
                minDistance = distance;
                minLat = coords[i][0];
                minLng = coords[i][1];
            }
        }
        drawShelterRoute({lat: posLat, lng: posLng}, {lat: minLat, lng: minLng});
    }
         */
    }

}
