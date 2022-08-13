package ua.edu.sumdu.nefodov.sheltered.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class AccessKey {

    @Id
    private String email;
    private String key;

    public AccessKey() {

    }

    public AccessKey(String email, String key) {
        this.email = email;
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessKey accessKey = (AccessKey) o;
        return Objects.equals(email, accessKey.email) && Objects.equals(key, accessKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, key);
    }
}
