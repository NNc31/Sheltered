package ua.edu.sumdu.nefodov.sheltered.model;

public enum ShelterConditions {
    WATER("Вода"),
    FOOD("Їжа"),
    ELECTRICITY("Електрика"),
    SEATS("Місця для сидіння"),
    WIFI("Вай-фай"),
    SOCKETS("Розетки"),
    RADIATION_PROTECTED("Протирадіаційне"),
    LIGHTING("Освітлення"),
    MEDICINES("Медикаменти");

    public final String label;

    ShelterConditions(String label) {
        this.label = label;
    }
}
