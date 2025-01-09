package service.model;

import lombok.Getter;

@Getter
public class Score {
    private int playerOneGame;
    private int playerOneSet;
    private int playerTwoGame;
    private int playerTwoSet;

    public static final int DEFAULT_PLAYER_ONE_GAME = 0;
    public static final int DEFAULT_PLAYER_TWO_GAME = 0;
    public static final int DEFAULT_PLAYER_ONE_SET = 0;
    public static final int DEFAULT_PLAYER_TWO_SET = 0;


    private Score(int playerOneGame, int playerOneSet, int playerTwoGame, int playerTwoSet) {
        this.playerOneGame = playerOneGame;
        this.playerOneSet = playerOneSet;
        this.playerTwoGame = playerTwoGame;
        this.playerTwoSet = playerTwoSet;
    }

    public Score() {
    this(
            DEFAULT_PLAYER_ONE_GAME,
            DEFAULT_PLAYER_ONE_SET,
            DEFAULT_PLAYER_TWO_GAME,
            DEFAULT_PLAYER_TWO_SET);
   }





}
