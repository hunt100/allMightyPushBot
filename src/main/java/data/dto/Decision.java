package data.dto;

import java.util.Objects;

public class Decision {
    private User user;
    private Boolean agree;

    public Decision() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return Objects.equals(user, decision.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
