package dto;

public class ScoreDto {
    private final String playerOneSet;
    private final String playerOneGame;
    private final String playerOnePoint;
    private final String playerTwoSet;
    private final String playerTwoGame;
    private final String playerTwoPoint;

    public ScoreDto(String playerOneSet, String playerOneGame, String playerOnePoint, String playerTwoSet, String playerTwoGame, String playerTwoPoint) {
        this.playerOneSet = playerOneSet;
        this.playerOneGame = playerOneGame;
        this.playerOnePoint = playerOnePoint;
        this.playerTwoSet = playerTwoSet;
        this.playerTwoGame = playerTwoGame;
        this.playerTwoPoint = playerTwoPoint;
    }

    public String getPlayerOneSet() {
        return playerOneSet;
    }

    public String getPlayerOneGame() {
        return playerOneGame;
    }

    public String getPlayerOnePoint() {
        return playerOnePoint;
    }

    public String getPlayerTwoSet() {
        return playerTwoSet;
    }

    public String getPlayerTwoGame() {
        return playerTwoGame;
    }

    public String getPlayerTwoPoint() {
        return playerTwoPoint;
    }
}
