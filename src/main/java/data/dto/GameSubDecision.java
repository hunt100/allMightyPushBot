package data.dto;

import data.enums.GameType;

public class GameSubDecision extends Decision {
    private final GameType type;

    public GameSubDecision(GameType type) {
        super();
        this.type = type;
    }

    public GameType getType() {
        return type;
    }
}
