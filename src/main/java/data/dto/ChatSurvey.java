package data.dto;

import data.enums.LeisureType;
import data.enums.LocationHost;

import java.util.HashSet;
import java.util.Set;

public class ChatSurvey {
    private Long chatId;
    private Set<Decision> decisions;
    private LeisureType type;

    public ChatSurvey(Long chatId, LeisureType type) {
        this.chatId = chatId;
        this.type = type;
        this.decisions = new HashSet<>();
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Set<Decision> getDecisions() {
        return decisions;
    }

    public void setDecisions(Set<Decision> decisions) {
        this.decisions = decisions;
    }

    public LeisureType getType() {
        return type;
    }

    public void setType(LeisureType type) {
        this.type = type;
    }

    public boolean isGeneralDecisionForUserExist(User user) {
        return isDecisionForUserWithTypeExist(user, LocationHost.GENERAL);
    }

    public boolean isDecisionForUserWithTypeExist(User user, LocationHost host) {
        return decisions
                .stream()
                .filter(decision -> user.getId().equals(decision.getUser().getId()))
                .anyMatch(decision -> host.equals(decision.getHost()));
    }
}
