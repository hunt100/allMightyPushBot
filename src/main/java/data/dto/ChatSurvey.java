package data.dto;

import data.enums.LeisureType;

import java.util.HashSet;
import java.util.Set;

public class ChatSurvey {
    private Long chatId;
    private Set<Decision> decisions;
    private Set<GameSubDecision> gameSubDecisions;
    private Set<WalkSubDecision> walkSubDecisions;
    private LeisureType type;

    public ChatSurvey(Long chatId, LeisureType type) {
        this.chatId = chatId;
        this.type = type;
        this.decisions = new HashSet<>();
        if (LeisureType.PLAY.equals(type)) {
            gameSubDecisions = new HashSet<>();
        } else {
            walkSubDecisions = new HashSet<>();
        }
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

    public Set<GameSubDecision> getGameSubDecisions() {
        return gameSubDecisions;
    }

    public void setGameSubDecisions(Set<GameSubDecision> gameSubDecisions) {
        this.gameSubDecisions = gameSubDecisions;
    }

    public Set<WalkSubDecision> getWalkSubDecisions() {
        return walkSubDecisions;
    }

    public void setWalkSubDecisions(Set<WalkSubDecision> walkSubDecisions) {
        this.walkSubDecisions = walkSubDecisions;
    }
}
