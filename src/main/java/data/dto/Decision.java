package data.dto;

import data.enums.LocationHost;

import java.util.Objects;

public class Decision {
    private User user;
    private LocationHost host;
    private Boolean agree;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocationHost getHost() {
        return host;
    }

    public void setHost(LocationHost host) {
        this.host = host;
    }

    public Boolean getAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Decision decision = (Decision) o;
        return Objects.equals(user, decision.user) && host == decision.host;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, host);
    }
}
