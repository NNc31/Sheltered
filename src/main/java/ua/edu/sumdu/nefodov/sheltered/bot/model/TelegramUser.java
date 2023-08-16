package ua.edu.sumdu.nefodov.sheltered.bot.model;

import ua.edu.sumdu.nefodov.sheltered.application.model.ShelterConditions;
import ua.edu.sumdu.nefodov.sheltered.application.model.ShelterStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "telegram_users")
public class TelegramUser {

    public TelegramUser() {

    }

    public TelegramUser(long chatId, double latitude, double longitude) {
        this.chatId = chatId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Id
    private long chatId;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @ElementCollection(targetClass = ShelterStatus.class)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "tg_user_statuses")
    private List<ShelterStatus> statuses;

    @ElementCollection(targetClass = ShelterConditions.class)
    @JoinTable(name = "tg_user_conditions")
    @Enumerated(EnumType.STRING)
    private List<ShelterConditions> conditions;

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<ShelterStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<ShelterStatus> statuses) {
        this.statuses = statuses;
    }

    public List<ShelterConditions> getConditions() {
        return conditions;
    }

    public void setConditions(List<ShelterConditions> conditions) {
        this.conditions = conditions;
    }
}
