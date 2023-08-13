package ua.edu.sumdu.nefodov.sheltered.application.model;

public enum ShelterStatus {
    IN_USE("Робоче"),
    UNDER_CONSTRUCTION("Будується"),
    BLOCKED("Заблоковане"),
    DESTROYED("Знищене"),
    ABANDONED("Покинуте"),
    UNKNOWN("Невідомо");

    public final String label;

    ShelterStatus(String label) {
        this.label = label;
    }
}
