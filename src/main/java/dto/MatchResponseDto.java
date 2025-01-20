package dto;

public class MatchResponseDto{
    private final String playerOne;
    private final String playerTwo;
    private final String winner;

    public MatchResponseDto(String playerOne, String playerTwo, String winner) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public String getPlayerOne() {
        return playerOne;
    }
}
